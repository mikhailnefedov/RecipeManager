package frontend;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import frontend.recipetab.RecipeTabController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

/**
 * Class Models controller of the main window.
 */
public class WindowController {

    @FXML
    private TableView<Recipe> recipeTable; //table view of recipes in frontend
    @FXML
    private TableColumn<Recipe, String> idColumn; //id column of recipeTable
    @FXML
    private TableColumn<Recipe, String> titleColumn; //title column " "
    @FXML
    private TableColumn<Recipe, String> categoryColumn; //category column " "
    @FXML
    private TableColumn<Recipe, Integer> timeColumn; //time column " "
    @FXML
    private TableColumn<Recipe, URL> sourceColumn; //source column " "
    @FXML
    private RecipeTabController recipeTabController; //injected controller

    /**
     * Initialization of window. Loads necessary data into the fxml components
     * of the view.
     */
    @FXML
    protected void initialize() {
        loadRecipesIntoTable();
    }

    /**
     * Loads the recipes into the table view.
     */
    private void loadRecipesIntoTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("RecipeCategory"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        recipeTable.setItems(getRecipes());
    }

    /**
     * Gets all saved recipes that are in the Singleton class of Recipes.
     *
     * @return list of all recipes
     */
    private ObservableList<Recipe> getRecipes() {
        return Recipes.getInstance().getSavedRecipes();
    }

    /**
     * When a recipe on the table is clicked then the data of the recipe is
     * shown in the tab.
     */
    public void handleTableViewClickOnRecipe() {
        Recipe selectedRecipe = recipeTable.getSelectionModel()
                .getSelectedItem();
        recipeTabController.loadRecipeDataIntoTab(selectedRecipe);
    }

}
