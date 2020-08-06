package backend.dataclasses.Recipe;

import java.util.ArrayList;

public class Recipes {

    private static Recipes instance = null;
    private static ArrayList<Recipe> savedRecipes;

    private Recipes() {

        savedRecipes = new ArrayList<Recipe>();
    }

    public static Recipes getInstance() {

        if (instance == null) {
            instance = new Recipes();
        }
        return instance;
    }

    //TODO: Add new Recipe

    public void addRecipes(ArrayList<Recipe.RecipeBuilder> builders) {
        for (Recipe.RecipeBuilder builder : builders) {
            savedRecipes.add(builder.build());
        }
    }

    public void addRecipe(Recipe.RecipeBuilder builder) {
        savedRecipes.add(builder.build());
    }

    public ArrayList<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

}
