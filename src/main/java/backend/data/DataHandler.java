package backend.data;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public final class DataHandler {

    /**
     * Initializes by loading data from database and creating new objects based
     * on this data.
     */
    public static void initialize() {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection
                    ("jdbc:sqlite:src/main/resources/RecipeManagerDB.db");

            GroceryCategoryReader.setConnectionToDatabase(connection);
            GroceryCategoryWriter.setConnectionToDatabase(connection);
            RecipeCategoryReader.setConnectionToDatabase(connection);
            RecipeCategoryWriter.setConnectionToDatabase(connection);
            RecipeReader.setConnectionToDatabase(connection);

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
     * @param cat category itself
     */
    public static void deleteRecipeCategory(RecipeCategory cat) {
        int number = RecipeCategoryReader.getNumberOfRecipesToCategory(cat);
        if (number == 0) {
            String id = cat.getId();
            RecipeCategoryWriter.removeCategory(id);
        } else throw new IllegalArgumentException();
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

    /**
     * Deletes grocery item from database.
     *
     * @param item grocery item itself
     * @throws IllegalArgumentException if the grocery item is used by a recipe
     */
    public static void deleteGroceryItem(GroceryItem item)
            throws IllegalArgumentException {
        int numberOfRecipes = GroceryCategoryReader
                .getNumberOfRecipesToCategory(item);
        if (numberOfRecipes == 0) {
            int id = item.getID();
            GroceryCategoryWriter.removeGroceryItem(id);
        } else throw new IllegalArgumentException();
    }

    /**
     * Changes the saved information of the grocery item in the database.
     *
     * @param item item itself
     * @param newName the changed name of the category
     * @param affiliatedCategory the changed category that the item belongs to
     */
    public static void changeGroceryItem(GroceryItem item, String newName,
                                         GroceryCategory affiliatedCategory) {

        int itemID = item.getID();
        int categoryID = affiliatedCategory.getID();
        GroceryCategoryWriter.changeItem(itemID, newName, categoryID);
    }

}
