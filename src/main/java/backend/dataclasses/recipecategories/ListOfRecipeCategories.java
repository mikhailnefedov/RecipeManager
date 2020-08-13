package backend.dataclasses.recipecategories;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ListOfRecipeCategories {

    private static ListOfRecipeCategories instance = null;
    private static ObservableList<RecipeCategory> savedRecipeCategories;

    private ListOfRecipeCategories() {

        savedRecipeCategories = FXCollections.observableArrayList(
                recipeCategory -> new Observable[]{recipeCategory.nameProperty(), recipeCategory.idProperty()});
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
     * @return true, if nonexistent | false, if name already exists
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
     * @param id id that shall be checked.
     * @return true, if nonexistent | false, if id already exists
     */
    public boolean isIDNonExistent(String id) {
        for (RecipeCategory category : savedRecipeCategories) {
            if (id.equals(category.getId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes Id for new category name. Will fail if category name already
     * exists.
     *
     * @param categoryName new category name
     * @return Id for new category
     * @throws IllegalArgumentException If creation of Id fails, reasons:
     *                                  category already in system or id
     *                                  creation not possible
     */
    public String computeIDForRecipeCategory(String categoryName)
            throws IllegalArgumentException {

        if (isCategoryNameNonExistent(categoryName)) {
            ArrayList<String> allIds = new ArrayList<>();
            for (RecipeCategory category : savedRecipeCategories) {
                allIds.add(category.getId());
            }
            return createID(categoryName, allIds);
        } else {
            throw new IllegalArgumentException("category name already exists");
        }

    }

    /**
     * Creates ID from new category name. Will not create an ID that already
     * exists. If 1 character ID is impossible, it will expand one character
     * further, etc... If a shorter ID is impossible, ID will be the name of the
     * category.
     *
     * @param categoryName new category name
     * @param allIds       all ids that are currently in the system
     * @return new Id
     * @throws IllegalArgumentException If every try for a potential id failed
     */
    public String createID(String categoryName, ArrayList<String> allIds)
            throws IllegalArgumentException {

        String potentialID;
        for (int i = 1; i <= categoryName.length(); i++) {
            potentialID = categoryName.substring(0, i);
            if (!allIds.contains(potentialID)) {
                return potentialID;
            }
        }
        // Should not reach this code
        throw new IllegalArgumentException("Something went wrong in the ID " +
                "creation, presumambly the newly created potential ID is " +
                "already in the system");
    }

    /**
     * Adds a new RecipeCategory to the saved list.
     *
     * @param categoryName name of the new category
     * @throws IllegalArgumentException If category with same name already
     *                                  exists
     */
    public void addRecipeCategory(String categoryName) throws IllegalArgumentException {
        String id = computeIDForRecipeCategory(categoryName);
        savedRecipeCategories.add(new RecipeCategory(id, categoryName));
    }
}
