package backend.data;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.Groceries;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

/**
 * Works as a facade for the database classes that handle the saving/updating/
 * deleting of objects.
 */
public final class DataHandler {

    /**
     * Initializes by loading data from database and creating new objects based
     * on this data.
     */
    public static void initialize() {

        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();

        RecipeCategoryHandler.initialize(sessionFactory);
        GroceryCategoryHandler.initialize(sessionFactory);
        RecipeHandler.initialize(sessionFactory);

        Session session = sessionFactory.openSession();

        Groceries shopList = Groceries.getInstance();
        shopList.initialize(GroceryCategoryHandler.getCategories(session));

        ArrayList<RecipeCategory> recipeCategories = RecipeCategoryHandler
                .getRecipeCategories(session);
        ListOfRecipeCategories lRecCats = ListOfRecipeCategories.getInstance();
        lRecCats.addListOfRecipeCategories(recipeCategories);

        ArrayList<Recipe> recipes = RecipeHandler.getRecipes(session);
        Recipes recipesInstance = Recipes.getInstance();
        recipesInstance.addRecipes(recipes);

        if (session.isOpen()) {
            session.close();
        }
    }

    /**
     * Updates the saved information of a recipe category in the database.
     *
     * @param categoryToUpdate category which shall be updated
     * @param newID            new id of the category
     * @param newName          new name of the category
     */
    public static void updateRecipeCategory(RecipeCategory categoryToUpdate,
                                            String newID, String newName) {

        RecipeCategoryHandler.updateCategory(categoryToUpdate, newID, newName);
    }

    /**
     * Saves new recipe category into the database.
     *
     * @param cat category to be saved
     */
    public static void saveNewRecipeCategory(RecipeCategory cat) {

        RecipeCategoryHandler.saveCategory(cat);
    }

    /**
     * Removes recipe category from the database.
     *
     * @param cat category itself
     */
    public static void deleteRecipeCategory(RecipeCategory cat) {
        int number = RecipeCategoryHandler.getNumberOfRecipesToCategory(cat);
        if (number == 0) {
            RecipeCategoryHandler.removeCategory(cat);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Creates a new grocery item and saves it into the database.
     *
     * @param category    category of the item
     * @param newItemName name of the item
     * @return the newly created GroceryItem
     */
    public static GroceryItem saveNewGroceryItem(GroceryCategory category,
                                                 String newItemName) {
        GroceryItem newItem = new GroceryItem(category, newItemName);
        GroceryCategoryHandler.saveItem(newItem);
        return newItem;
    }

    /**
     * Deletes grocery item from database.
     *
     * @param item grocery item itself
     * @throws IllegalArgumentException if the grocery item is used by a recipe
     */
    public static void deleteGroceryItem(GroceryItem item)
            throws IllegalArgumentException {
        int numberOfRecipes = GroceryCategoryHandler
                .getNumberOfRecipesToCategory(item);
        System.out.println(numberOfRecipes);
        if (numberOfRecipes == 0) {
            GroceryCategoryHandler.removeGroceryItem(item);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Changes the saved information of the grocery item in the database.
     *
     * @param item               item itself
     * @param newName            the changed name of the category
     * @param affiliatedCategory the changed category that the item belongs to
     */
    public static void updateGroceryItem(GroceryItem item, String newName,
                                         GroceryCategory affiliatedCategory) {

        GroceryCategoryHandler.updateItem(item, affiliatedCategory, newName);
    }


}
