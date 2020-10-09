package backend.data;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gets database records from GroceryCategory and GroceryItem tables.
 */
public final class GroceryCategoryHandler {

    private static SessionFactory sessionFactory;

    private static EntityManager entityManager;
    /**
     * Connection to RecipeManagerDB.
     */
    private static Connection connection;

    private GroceryCategoryHandler() {
    }

    /**
     * Initializes SessionFactory and EntityManager.
     *
     * @param sF         SessionFactory of program
     * @param entManager Entitymanager of program
     */
    public static void initialize(SessionFactory sF, EntityManager entManager) {
        sessionFactory = sF;
        entityManager = entManager;
    }

    /**
     * Sets the connection to the database.
     *
     * @param c Connection to database (should be to RecipeManagerDB)
     */
    public static void setConnectionToDatabase(Connection c) {
        connection = c;
    }

    /**
     * Gets the grocery categories and their grocery items as a HashMap from the
     * database.
     *
     * @return HashMap with grocery categories and their items.
     */
    public static HashMap<GroceryCategory, ArrayList<GroceryItem>>
    readCategories() {

        HashMap<GroceryCategory, ArrayList<GroceryItem>> categoriesAndItems =
                new HashMap<>();

        Session session = sessionFactory.openSession();

        List categories = new ArrayList();
        try {
            categories = session.createQuery("from GroceryCategory ").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        for (GroceryCategory cat : (ArrayList<GroceryCategory>) categories) {
            categoriesAndItems.put(cat, getItems(session, cat));
        }

        if (session.isOpen()) {
            session.close();
        }

        return categoriesAndItems;
    }

    /**
     * Gets the items from a specific category from the database.
     *
     * @param session  currently opened session when getting grocery categories.
     * @param category category to which the items belong
     * @return List of items of the category
     */
    private static ArrayList<GroceryItem> getItems(Session session,
                                                   GroceryCategory category) {

        List items = new ArrayList();
        try {
            Query query = session.createQuery("from GroceryItem " +
                    "WHERE groceryCategory =:category");
            query.setParameter("category", category);
            items = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return (ArrayList<GroceryItem>) items;
    }

    /**
     * Returns number of recipes that use the grocery item from database.
     *
     * @param item grocery item used/not used by recipes
     * @return number of recipes
     */
    public static int getNumberOfRecipesToCategory(GroceryItem item) {

        Session session = sessionFactory.openSession();
        int sum = 0;

        try {
            javax.persistence.Query query = session.createSQLQuery(
                    "SELECT COUNT(r.id) AS total"
                            + " FROM Recipe r, uses u, GroceryItem g"
                            + " WHERE r.id = u.recipeID"
                            + " AND u.groceryitemID = g.id"
                            + " AND g.id = " + item.getID() + ";");

            sum = ((Number) query.getSingleResult()).intValue();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        System.out.println(sum);
        System.out.println("----");
        return sum;
    }

    /**
     * Inserts a new record for the new grocery item into the database.
     *
     * @param newItem item to be persisted
     */
    public static void saveItem(GroceryItem newItem) {

        entityManager.getTransaction().begin();
        entityManager.persist(newItem);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    /**
     * Removes record of grocery item from database.
     *
     * @param item item to be removed
     */
    public static void removeGroceryItem(GroceryItem item) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(item);
        tx.commit();
        session.close();
    }

    /**
     * Updates the grocery item record in the database.
     *
     * @param item               item that shall be updated
     * @param affiliatedCategory the new category to which it affiliates
     * @param newName            new name of the item
     */
    public static void updateItem(GroceryItem item,
                                  GroceryCategory affiliatedCategory,
                                  String newName) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        item.updateAffiliatedGroceryCategory(affiliatedCategory);
        item.updateName(newName);
        session.update(item);
        tx.commit();
        session.close();
    }

}
