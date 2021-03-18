package backend.dataclasses.recipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Recipes {

    private static Recipes instance = null;
    private static ObservableList<Recipe> savedRecipes;

    private Recipes() {
        savedRecipes = FXCollections.observableArrayList();
    }

    public static Recipes getInstance() {
        if (instance == null) {
            instance = new Recipes();
        }
        return instance;
    }

    public void addRecipes(ArrayList<Recipe> recipes) {
        savedRecipes.addAll(recipes);
    }

    public void addRecipe(Recipe recipe) {
        savedRecipes.add(recipe);
    }

    public ObservableList<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

    public void removeRecipe(Recipe recipe) {
        savedRecipes.remove(recipe);
    }

}
