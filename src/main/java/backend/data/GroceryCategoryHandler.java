package backend.data;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handles the GroceryCategory and GroceryItem tables from the database.
 */
public final class GroceryCategoryHandler {

    /**
     * Unique SessionFactory from DataHandler.
     */
    private static SessionFactory sessionFactory;

    private GroceryCategoryHandler() {
    }

    /**
     * Initializes SessionFactory and EntityManager.
     *
     * @param sF SessionFactory of program
     */
    public static void initialize(SessionFactory sF) {
        sessionFactory = sF;
    }

    /**
     * Gets the grocery categories and their grocery items as a HashMap from the
     * database. Usage for initialization. Use only combined with getting all
     * the other data from the database.
     *
     * @param session current loading session (all categories, recipes, etc...)
     * @return HashMap with grocery categories and their items.
     */
    public static HashMap<GroceryCategory, ArrayList<GroceryItem>>
    getCategories(Session session) {

        HashMap<GroceryCategory, ArrayList<GroceryItem>> categoriesAndItems =
                new HashMap<>();

        List categories = new ArrayList();
        try {
            categories = session.createQuery("from GroceryCategory ").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        for (GroceryCategory cat : (ArrayList<GroceryCategory>) categories) {
            categoriesAndItems.put(cat, getItems(session, cat));
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
            Query query = session.createQuery("from GroceryItem "
                    + " WHERE groceryCategory =:category");
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
            Query query = session.createSQLQuery(
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
        return sum;
    }

    /**
     * Inserts a new record for the new grocery item into the database.
     *
     * @param newItem item to be persisted
     */
    public static void saveItem(GroceryItem newItem) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(newItem);
        tx.commit();
        session.close();
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
