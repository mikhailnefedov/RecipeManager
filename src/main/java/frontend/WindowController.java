package frontend;

import backend.data.RecipeHandler;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import frontend.recipetab.RecipeTabController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    private ReadOnlyObjectProperty<Recipe> currentRecipe;

    /**
     * Initialization of window. Loads necessary data into the fxml components
     * of the view.
     */
    @FXML
    protected void initialize() {
        loadRecipesIntoTable();
        bindCurrentRecipe();
        bindButtons();
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

    private void bindCurrentRecipe() {
        currentRecipe = recipeTable.getSelectionModel().selectedItemProperty();
        currentRecipe.addListener((observableValue, o, t1) -> {
            recipeTabController.loadRecipeDataIntoTab(currentRecipe.get());
        });
    }

    private void bindButtons() {
        deleteButton.disableProperty().bind(currentRecipe.isNull());
    }

    public void addClick() {
        Recipe newRecipe = new Recipe();
        Recipes.getInstance().addRecipe(newRecipe);
    }

    public void deleteClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                currentRecipe.get().getTitle() + " löschen?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            RecipeHandler.deleteRecipe(
                    recipeTable.getSelectionModel().getSelectedItem());
            Recipes.getInstance().removeRecipe(currentRecipe.get());
        }
    }

}
