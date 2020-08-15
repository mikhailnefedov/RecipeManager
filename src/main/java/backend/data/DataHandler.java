package backend.data;

import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public final class DataHandler {

    /**
     * Currently work in progress. Initializes data loading from xml files and
     * creates new objects based on the data.
     *
     * @throws FileNotFoundException If xml files that will be read/written
     *                               from/to are not existing
     */
    public static void initialize() throws FileNotFoundException {

        HashMap<String, ArrayList<String>> catsAndItems =
                GroceryCategoryReader.readCategories();

        ShoppingList shopList = ShoppingList.getInstance();
        for (String category : catsAndItems.keySet()) {
            shopList.addCategoryWithItems(category, catsAndItems.get(category));
        }

        ArrayList<RecipeCategory> recipeCategories = RecipeCategoryReader.readRecipeCategories();
        ListOfRecipeCategories listOfRecCats = ListOfRecipeCategories.getInstance();
        listOfRecCats.addListOfRecipeCategories(recipeCategories);

        ArrayList<Recipe.RecipeBuilder> recipeBuilders = RecipeReader.readRecipes();
        Recipes recipes = Recipes.getInstance();
        recipes.addRecipes(recipeBuilders);

    }

    /**
     * Changes saved information of a recipe category.
     *
     * @param oldID      current id of category
     * @param oldCatName current name of category
     * @param newID      new id of category
     * @param newCatName new name of category
     */
    public static void changeRecipeCategory(String oldID, String oldCatName,
                                            String newID, String newCatName) {

        try {
            RecipeCategoryWriter.changeCategory(oldID, oldCatName,
                    newID, newCatName);
            //TODO: Update Recipes of the changed category
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves new recipe category into a file.
     *
     * @param id           id of new category
     * @param categoryName name of new category
     */
    public static void saveNewRecipeCategory(String id, String categoryName) {

        try {
            RecipeCategoryWriter.writeNewCategory(id, categoryName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes recipe category from save file.
     *
     * @param id           id of category
     * @param categoryName name of category
     */
    public static void deleteRecipeCategory(String id, String categoryName) {

        try {
            RecipeCategoryWriter.removeCategory(id, categoryName);
            //TODO: Handle Recipes with the deleted categories so that they get a dummy id
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
