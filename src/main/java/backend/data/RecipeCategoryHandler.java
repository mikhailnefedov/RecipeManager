package backend.data;

import backend.dataclasses.recipecategories.RecipeCategory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the RecipeCategory table from the database.
 */
public final class RecipeCategoryHandler {

    /**
     * Unique SessionFactory from DataHandler.
     */
    private static SessionFactory sessionFactory;

    private RecipeCategoryHandler() {
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
     * Gets a list with the saved recipe categories from the database. Usage for
     * initialization. Use only combined with getting all the other data from
     * the database.
     *
     * @param session current loading session (all categories, recipes, etc...)
     * @return list of saved recipe categories
     */
    public static ArrayList<RecipeCategory>
    getRecipeCategories(Session session) {

        List categories = new ArrayList();
        try {
            categories = session.createQuery("from RecipeCategory ").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return (ArrayList<RecipeCategory>) categories;
    }

    /**
     * Returns number of recipes associated to recipe category from database.
     *
     * @param category recipes are associated with this category
     * @return number of recipes
     */
    public static int getNumberOfRecipesToCategory(RecipeCategory category) {

        Session session = sessionFactory.openSession();
        int sum = 0;

        try {
            Query query = session.createSQLQuery(
                    "SELECT COUNT(r.id) AS total"
                            + " FROM Recipe r, RecipeCategory c"
                            + " WHERE r.recipecategoryID = c.id and c.id = '"
                            + category.getID() + "';");

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
     * Updates record of recipe category.
     *
     * @param recipeCategory category itself
     * @param newID          new id of recipe category
     * @param newName        new name of recipe category
     */
    public static void updateCategory(RecipeCategory recipeCategory,
                                      String newID, String newName) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        recipeCategory.setAbbreviation(newID);
        recipeCategory.setName(newName);
        session.update(recipeCategory);
        tx.commit();
        session.close();
    }

    /**
     * Adds new record for the new recipe category.
     *
     * @param recipeCategory recipeCategory to persist
     */
    public static void saveCategory(RecipeCategory recipeCategory) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(recipeCategory);
        tx.commit();
        session.close();
    }

    /**
     * Removes record of recipe category from database.
     *
     * @param category category that shall be removed from the database
     */
    public static void removeCategory(RecipeCategory category) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(category);
        tx.commit();
        session.close();
    }

}
