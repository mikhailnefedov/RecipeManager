package backend.data;

import backend.dataclasses.recipecategories.RecipeCategory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets data from RecipeCategory table from database.
 */
public final class RecipeCategoryHandler {

    /**
     * Creater of sessions.
     */
    private static SessionFactory sessionFactory;

    private static EntityManager entityManager;

    private RecipeCategoryHandler() {
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
     * Gets a list with the saved recipe categories from the database.
     *
     * @return list of saved recipe categories
     */
    public static ArrayList<RecipeCategory> readRecipeCategories() {

        Session session = sessionFactory.openSession();

        List resultCategories = new ArrayList();
        try {
            resultCategories = session.createQuery("from RecipeCategory ").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return (ArrayList<RecipeCategory>) resultCategories;
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
                            + category.getId() + "';");

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
        recipeCategory.updateID(newID);
        recipeCategory.updateName(newName);
        session.update(recipeCategory);
        tx.commit();
        session.close();
    }

    /**
     * Adds new record for the new recipe category.
     *
     * @param recipeCategory recipeCategory to persist
     */
    public static void writeNewCategory(RecipeCategory recipeCategory) {

        entityManager.getTransaction().begin();
        entityManager.persist(recipeCategory);
        entityManager.getTransaction().commit();
    }

    /**
     * Removes record of recipe category from database.
     *
     * @param category category that shall be removed from the database
     */
    public static void removeCategory(RecipeCategory category) {

        entityManager.getTransaction().begin();
        category = entityManager.find(RecipeCategory.class, category.getId());
        entityManager.remove(category);
        entityManager.getTransaction().commit();
    }

}
