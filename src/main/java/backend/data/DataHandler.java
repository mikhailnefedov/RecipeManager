package backend.data;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public final class DataHandler {

    /**
     * Currently work in progress. Initializes data loading from xml files and
     * creates new objects based on the data.
     *
     * @throws FileNotFoundException If xml files that will be read/written
     *                               from/to are not existing
     */
    public static void initialize() throws FileNotFoundException {


        //New part for database:
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection
                    ("jdbc:sqlite:src/main/resources/RecipeManagerDB.db");

            GroceryCategoryReader.setConnectionToDatabase(connection);
            GroceryCategoryWriter.setConnectionToDatabase(connection);
            RecipeCategoryReader.setConnectionToDatabase(connection);
            RecipeCategoryWriter.setConnectionToDatabase(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }


        ShoppingList shopList = ShoppingList.getInstance();
        shopList.initialize(GroceryCategoryReader.readCategories());

        ArrayList<RecipeCategory> recipeCategories = RecipeCategoryReader.readRecipeCategories();
        ListOfRecipeCategories listOfRecCats = ListOfRecipeCategories.getInstance();
        listOfRecCats.addListOfRecipeCategories(recipeCategories);

        ArrayList<Recipe.RecipeBuilder> recipeBuilders = RecipeReader.readRecipes();
        Recipes recipes = Recipes.getInstance();
        recipes.addRecipes(recipeBuilders);


    }

    /**
     * Changes saved information of a recipe category in the database.
     *
     * @param oldID      current id of category
     * @param newID      new id of category
     * @param newCatName new name of category
     */
    public static void changeRecipeCategory(String oldID, String newID,
                                            String newCatName) {

            RecipeCategoryWriter.changeCategory(oldID, newID, newCatName);
    }

    /**
     * Saves new recipe category into the database.
     *
     * @param id           id of new category
     * @param categoryName name of new category
     */
    public static void saveNewRecipeCategory(String id, String categoryName) {

        RecipeCategoryWriter.writeNewCategory(id, categoryName);
    }

    /**
     * Removes recipe category from the database.
     *
     * @param id id of category
     */
    public static void deleteRecipeCategory(String id) {

        RecipeCategoryWriter.removeCategory(id);
    }

    /**
     * Adds the new grocery item string into the database
     *
     * @param category    category of the item
     * @param newItemName name of the item
     * @return id of newly added item into the database
     */
    public static int saveNewGroceryItem(GroceryCategory category,
                                         String newItemName) {
        return GroceryCategoryWriter.writeItem(category, newItemName);
    }
}
