package backend.data;

import backend.dataclasses.Recipe.Recipe;
import backend.dataclasses.Recipe.Recipes;
import backend.dataclasses.ShoppingList;

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
                CategoryReader.readCategories();

        ShoppingList shopList = ShoppingList.getInstance();
        for (String category : catsAndItems.keySet()) {
            shopList.addCategoryWithItems(category, catsAndItems.get(category));
        }

        ArrayList<Recipe.RecipeBuilder> recipeBuilders = RecipeReader.readRecipes();
        Recipes recipes = Recipes.getInstance();
        recipes.addRecipes(recipeBuilders);
        System.out.print(recipes.getSavedRecipes().toString());


    }
}
