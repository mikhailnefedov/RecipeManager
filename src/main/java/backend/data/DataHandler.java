package backend.data;

import backend.dataclasses.Recipe.Recipe;
import backend.dataclasses.Recipe.Recipes;
import backend.dataclasses.ShoppingList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataHandler {

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
