package frontend.recipetab;

import backend.data.DataHandler;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.uses.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class IngredientTableWidgetController extends RecipeWidgetsController {
    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, String> categoryColumn;
    @FXML
    private TableColumn<Ingredient, String> itemColumn;
    @FXML
    private TableColumn<Ingredient, String> quantityColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    private Recipe recipe;
    private ArrayList<Ingredient> ingredientsToRemove;

    /**
     * Initialization of IngredientTabWidget. Sets the cell values of the table.
     */
    @FXML
    public void initialize() {
        super.initialize();

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryString"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemString"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ingredientsToRemove = new ArrayList<>();
    }

    public void enableEdit() {
        ingredientTable.setDisable(false);
        addButton.setDisable(false);
        editButton.disableProperty().bind(ingredientTable
                .getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(ingredientTable
                .getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Shows the list of ingredients of the recipe.
     *
     * @param recipe recipe itself
     */
    public void showIngredients(Recipe recipe) {
        this.recipe = recipe;
        ingredientTable.setItems(recipe.getObservableIngredients());
    }

    public void addIngredient() {

    }

    public void editIngredient() {

    }

    /**
     * Deletes the selected ingredient from the recipe.
     */
    public void deleteIngredient() {
        changeDetected.setValue(true);
        recipe.getObservableIngredients().remove(
                ingredientTable.getSelectionModel().getSelectedItem());
        ingredientsToRemove.add(
                ingredientTable.getSelectionModel().getSelectedItem());
    }

    public void saveChanges() {
        DataHandler.deleteIngredient(ingredientsToRemove);
    }
}
