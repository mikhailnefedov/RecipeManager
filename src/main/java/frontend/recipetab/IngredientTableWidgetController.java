package frontend.recipetab;

import backend.data.RecipeHandler;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.uses.Ingredient;
import frontend.helper.WindowLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
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

    /**
     * Initializes/Binds the disableProperty of the visible fxml elements.
     */
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

    /**
     * Opens the Edit widget of the ingredients in a new stage.
     *
     * @return Controller of the edit widget.
     * @throws IOException
     */
    public IngredientEditWidgetController openEditWidget() throws IOException {
        IngredientEditWidgetController controller = (IngredientEditWidgetController)
                WindowLoader.openNewWindowReturnController("recipeTab/IngredientEditWidget",
                        "Füge neue Zutat hinzu");
        controller.setParentController(this);
        return controller;
    }

    /**
     * Adds a new ingredient to the current recipe and to the saveOrUpdate list.
     *
     * @param ingredient the newly created ingredient.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(recipe);
        changeDetected.setValue(true);
        recipe.getObservableIngredients().add(ingredient);
    }

    /**
     * Opens and initializes the Edit widget of the ingredients for the
     * edit mode.
     *
     * @throws IOException
     */
    public void editIngredient() throws IOException {
        IngredientEditWidgetController controller = openEditWidget();
        controller.setCurrentItem(ingredientTable.getSelectionModel()
                .getSelectedItem());
        controller.activateEditing();
    }

    /**
     * Updates the current ingredient with new data.
     *
     * @param updatedIngredient
     */
    public void updateIngredient(Ingredient updatedIngredient) {
        changeDetected.setValue(true);
        Ingredient currentIngredient = ingredientTable.getSelectionModel().getSelectedItem();
        currentIngredient.setGroceryItem(updatedIngredient.getItem());
        currentIngredient.setQuantity(updatedIngredient.getQuantity());

        ingredientTable.refresh();
    }

    /**
     * Deletes the selected ingredient from the recipe.
     */
    public void deleteIngredient() {
        changeDetected.setValue(true);
        ingredientsToRemove.add(
                ingredientTable.getSelectionModel().getSelectedItem());
        recipe.getObservableIngredients().remove(
                ingredientTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Saves the changes made to the ingredient list of the recipe or the
     * ingredients themselves respectively.
     */
    public void saveChanges() {
        if (!ingredientsToRemove.isEmpty()) {
            RecipeHandler.deleteIngredients(ingredientsToRemove);
        }
    }
}
