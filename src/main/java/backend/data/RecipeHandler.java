package backend.data;

import backend.dataclasses.recipe.Recipe;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

}
