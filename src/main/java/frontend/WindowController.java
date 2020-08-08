package frontend;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

public class WindowController {

    @FXML
    private TableView<Recipe> recipeTable;
    @FXML
    private TableColumn<Recipe, String> idColumn;
    @FXML
    private TableColumn<Recipe, String> titleColumn;
    @FXML
    private TableColumn<Recipe, String> categoryColumn;
    @FXML
    private TableColumn<Recipe, Integer> timeColumn;
    @FXML
    private TableColumn<Recipe, URL> sourceColumn;

    private ObservableList<Recipe> getRecipes() {
        return Recipes.getInstance().getSavedRecipes();
    }

    private void loadDataIntoTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("recipeLink"));

        recipeTable.setItems(getRecipes());
    }


    @FXML
    protected void initialize() {

        loadDataIntoTable();
    }
}
