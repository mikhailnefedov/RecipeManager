package frontend.recipetab;

import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipe.uses.Quantity;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.function.Predicate;

public class IngredientEditWidgetController {

    @FXML
    private TableView<GroceryItem> itemsTable;
    @FXML
    private TableColumn<GroceryItem, String> categoryColumn;
    @FXML
    private TableColumn<GroceryItem, String> itemColumn;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label itemLabel;
    @FXML
    private TextField amountTextField;
    @FXML
    private ComboBox<Quantity.MeasurementUnits> unitComboBox;
    @FXML
    private TextField searchBar;
    @FXML
    private Button saveButton;
    private IngredientTableWidgetController parentController;
    private ReadOnlyObjectProperty currentItem;
    private boolean edit;


    @FXML
    protected void initialize() {
        loadItemsIntoTable();
        initializeLabels();
        initializeAmountTextField();
        initializeUnitComboBox();
        bindSaveButton();
        edit = false;
    }

    /**
     * Loads the grocery items into the table view.
     */
    private void loadItemsIntoTable() {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("affiliatedCategory"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        FilteredList<GroceryItem> filteredList = new FilteredList<>(getGroceryItems());
        itemsTable.setItems(filteredList);

        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(createPredicate(newValue)));
    }

    /**
     * Gets the grocery items that currently exist in the program.
     *
     * @return list of every grocery item
     */
    private ObservableList<GroceryItem> getGroceryItems() {
        return ShoppingList.getInstance().getObservableItems();
    }

    /**
     * Creates a predicate used for filtering the list to search items that
     * contain the searchText.
     * @param searchText input from user to filter the list.
     * @return predicate
     */
    private Predicate<GroceryItem> createPredicate(String searchText) {
        return groceryItem -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return (groceryItem.getName().toLowerCase().contains(searchText.toLowerCase()));
        };
    }

    /**
     * Initializes the labels to dynamically change to the selection of the user
     * in the table view.
     */
    private void initializeLabels() {
        itemsTable.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldItem, newItem) ->
                        setLabels(newItem));
        currentItem = itemsTable.getSelectionModel().selectedItemProperty();
    }

    /**
     * Sets the labels of this widget with the data of a groceryItem.
     * @param item the grocery item itself.
     */
    private void setLabels(GroceryItem item) {
        categoryLabel.setText(item.getGroceryCategory().getName());
        itemLabel.setText(item.getName());
    }

    /**
     * Initializes the amount textfield to only be able to contain numbers.
     */
    private void initializeAmountTextField() {
        amountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    amountTextField.setText(oldValue);
                }
            }
        });
    }

    /**
     * Loads the MeasurementUnits into the unit combobox.
     */
    private void initializeUnitComboBox() {
        unitComboBox.getItems().addAll(Quantity.getAllMeasurementUnits());
    }

    /**
     * Binds the save button to properties, so that a save click is only
     * possible if every data field is filled out by the user.
     */
    private void bindSaveButton() {
        saveButton.disableProperty().bind(currentItem.isNull()
                .or(amountTextField.textProperty().isEmpty())
                .or(unitComboBox.getSelectionModel().selectedItemProperty()
                        .isNull()));
    }

    /**
     * Sets the parent controller of this widget.
     * @param controller Controller that opened this widget
     */
    public void setParentController(IngredientTableWidgetController controller) {
        this.parentController = controller;
    }

    /**
     * Loads the ingredient data into the parent controller.
     */
    public void save() {
        Quantity quantity = new Quantity(amountTextField.textProperty().get(),
                unitComboBox.getSelectionModel().getSelectedItem());
        Ingredient ingredient = new Ingredient(
                (GroceryItem) currentItem.get(), quantity);
        if (edit) {
            parentController.updateIngredient(ingredient);
        } else {
            parentController.addIngredient(ingredient);
        }
        closeStage();
    }

    /**
     * Closes this widget. (Closes the stage).
     */
    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the current ingredient item and updates every frontend element to
     * represent its data. (Used for the edit mode)
     * @param ingredient the ingredient to be edited.
     */
    public void setCurrentItem(Ingredient ingredient) {
        itemsTable.getSelectionModel().select(ingredient.getItem());
        amountTextField.setText(Double.toString(ingredient.getQuantity()
                .getAmount()));
        unitComboBox.getSelectionModel().select(ingredient.getQuantity()
                .getMeasurementUnit());
    }

    /**
     * Sets the boolean variable edit to true. Activates the edit mode of this
     * widget, used for editing existing ingredients.
     */
    public void activateEditing() {
        edit = true;
    }
}
