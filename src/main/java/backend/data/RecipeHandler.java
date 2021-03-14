package backend.data;

import backend.dataclasses.recipe.PreparationStep;
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
     * Deletes record of the ingredient.
     *
     * @param ingredients list of ingredients that shall be deleted
     */
    public static void deleteIngredients(ArrayList<Ingredient> ingredients) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (Ingredient i : ingredients) {
            session.remove(i);
        }
        tx.commit();
        session.close();
    }

    /**
     * Saves/Updates the records of the ingredients.
     *
     * @param ingredients list of ingredients that shall be updated
     */
    public static void saveOrUpdateIngredients(ArrayList<Ingredient> ingredients) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (Ingredient i : ingredients) {
            session.saveOrUpdate(i);
        }
        tx.commit();
        session.close();
    }

    /**
     * Updates the recipe and persists the change.
     *
     * @param recipe            recipe that shall be updated
     * @param recipeWithNewData recipe with the updated data in it.
     */
    public static void updateRecipe(Recipe recipe, Recipe recipeWithNewData) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        recipe.copyRecipeMetaData(recipeWithNewData);
        session.update(recipe);
        tx.commit();
        session.close();
    }

    /**
     * Saves/Updates the records of the preparation instructions.
     *
     * @param recipe the instruction of this recipe will be updated
     */
    public static void updateInstructions(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (PreparationStep p : recipe.getObservablePreparation()) {
            if (p.getRecipe() == null) {
                p.setRecipe(recipe);
            }
            session.saveOrUpdate(p);
        }
        tx.commit();
        session.close();
    }

}
