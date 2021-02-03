package backend.data;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.uses.Ingredient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the Recipe table from the database.
 */
public final class RecipeHandler {

    /**
     * Unique SessionFactory from DataHandler.
     */
    private static SessionFactory sessionFactory;

    private RecipeHandler() {
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
     * Gets a list with the saved recipes from the database. Usage for
     * initialization. Use only combined with getting all the other data from
     * the database.
     *
     * @param session current loading session (all categories, recipes, etc...)
     * @return list of saved recipes
     */
    public static ArrayList<Recipe> getRecipes(Session session) {

        List recipesTest = new ArrayList();
        try {
            recipesTest = session.createQuery("from Recipe ").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return (ArrayList<Recipe>) recipesTest;
    }

    /**
     * Updates record of recipe.
     *
     * @param recipe recipe itself
     */
    public static void updateRecipe(Recipe recipe) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        //Update here
        session.update(recipe);
        tx.commit();
        session.close();
    }

    /**
     * Deletes record of the ingredient.
     *
     * @param ingredients ingredient itself
     */
    public static void deleteIngredient(ArrayList<Ingredient> ingredients) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (Ingredient i : ingredients) {
            session.remove(i);
        }
        tx.commit();
        session.close();
    }

}
