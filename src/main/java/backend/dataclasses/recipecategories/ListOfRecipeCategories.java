package backend.dataclasses.recipecategories;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ListOfRecipeCategories {

    private static ListOfRecipeCategories instance = null;
    /**
     * Observable list that contains every recipe Category loaded from the xml
     * or added/changed/deleted at runtime.
     */
    private static ObservableList<RecipeCategory> savedRecipeCategories;

    /**
     * Constructor. Creates a new ObservableArrayList for the table view of the
     * frontend. Reason: Changes to backend data are visible live in frontend
     * part
     */
    private ListOfRecipeCategories() {

        savedRecipeCategories = FXCollections.observableArrayList(
                recipeCategory -> new Observable[]
                        {recipeCategory.nameProperty(),
                                recipeCategory.abbreviationProperty()});
        //Callback exists, so that if there is a change to a RecipeCategory item
        //the frontend table view will be updated
    }

    public static ListOfRecipeCategories getInstance() {

        if (instance == null) {
            instance = new ListOfRecipeCategories();
        }
        return instance;
    }

    public ObservableList<RecipeCategory> getSavedRecipeCategories() {
        return savedRecipeCategories;
    }

    /**
     * Adds RecipeCategories from a list to an observablelist for frontend.
     *
     * @param categories list with recipecategories
     */
    public void addListOfRecipeCategories(
            ArrayList<RecipeCategory> categories) {

        savedRecipeCategories.addAll(categories);
    }

    /**
     * Checks if a category name is already in program.
     *
     * @param categoryName name that shall be checked.
     * @return true, if nonexistent | false, if name already exists or empty
     */
    public boolean isCategoryNameNonExistent(String categoryName) {
        for (RecipeCategory category : savedRecipeCategories) {
            if (categoryName.equals(category.getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if an id is already given.
     *
     * @param abbreviation abbreviation that shall be checked.
     * @return true, if nonexistent | false, if id already exists or empty
     */
    public boolean isAbbreviationNonExistent(String abbreviation) {
        for (RecipeCategory category : savedRecipeCategories) {
            if (abbreviation.equals(category.getAbbreviation())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a new RecipeCategory to the saved list.
     *
     * @param recipeCategory category to be added
     */
    public void addRecipeCategory(RecipeCategory recipeCategory) {
        savedRecipeCategories.add(recipeCategory);
    }

    /**
     * Gets the recipe category object that equals the param name.
     *
     * @param recipeCategory name of the category
     * @return recipe category object
     */
    public RecipeCategory getRecipeCategory(String recipeCategory) {
        return savedRecipeCategories.stream()
                .filter(r -> r.getName().equals(recipeCategory))
                .findFirst().get();
    }
}
