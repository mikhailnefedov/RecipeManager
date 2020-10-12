package backend.data;

import backend.dataclasses.recipe.uses.Quantity;
import backend.dataclasses.recipe.Recipe;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader for Recipe table from database.
 */
public final class RecipeHandler {

    private static SessionFactory sessionFactory;

    private static EntityManager entityManager;

    private RecipeHandler() {
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
