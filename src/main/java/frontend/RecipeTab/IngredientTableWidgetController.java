package frontend.RecipeTab;

import backend.dataclasses.recipe.uses.Ingredient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class IngredientTableWidgetController {
    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, String> categoryColumn;
    @FXML
    private TableColumn<Ingredient, String> itemColumn;
    @FXML
    private TableColumn<Ingredient, String> quantityColumn;

    /**
     * Initialization of IngredientTabWidget. Sets the cell values of the table.
     */
    @FXML
    protected void initialize() {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryString"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemString"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    /**
     * Shows the list of ingredients of a recipe in the table.
     * @param ingredients list of ingredients of a recipe.
     */
    public void showIngredients(ObservableList<Ingredient> ingredients) {
        ingredientTable.setItems(ingredients);
    }
}
