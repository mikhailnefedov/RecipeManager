package frontend;

import backend.data.DataHandler;
import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class GroceryItemsWindowController {

    @FXML
    private TableView<GroceryItem> groceryTable; //grocery items table view
    @FXML
    private TableColumn<GroceryItem, String> groceryTableCategoryColumn;
    @FXML
    private TableColumn<GroceryItem, String> groceryTableItemColumn;
    @FXML
    private ComboBox<GroceryCategory> groceryCategoryComboBox;
    @FXML
    private TextField groceryItemTextField;
    @FXML
    private Button groceryChangeItemButton;
    @FXML
    private Button groceryDeleteItemButton;
    @FXML
    private Button grocerySaveButton;
    @FXML
    private Label groceryItemErrorLabel;
    /**
     * String representing the current state of input. Values: "newItem", "changeItem"
     */
    private String currentState = "";
    /**
     * Represents tooltip for showing user specific errors on his input.
     */
    private ErrorTooltip errorTooltip;

    /**
     * Initializes window. Loads necessary data into table view and sets other
     * view specific things.
     */
    @FXML
    protected void initialize() {
        loadItemsIntoCategoryTable();
        groceryTable.getSortOrder().add(groceryTableCategoryColumn);

        groceryCategoryComboBox.getItems().addAll(      //load into combobox
                ShoppingList.getInstance().getGroceryCategories());

        errorTooltip = new ErrorTooltip();
        groceryItemErrorLabel.setTooltip(errorTooltip);
    }

    /**
     * Loads the grocery items into the table view.
     */
    private void loadItemsIntoCategoryTable() {
        groceryTableCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("affiliatedCategory"));
        groceryTableItemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groceryTable.setItems(getGroceryItems());
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
     * @return selected grocery item from table view (groceryTable).
     */
    private GroceryItem getSelectedItem() {
        return groceryTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Handler for a click on the new item button. Enables Textfield and
     * Combobox for creating a new grocery item.
     */
    @FXML
    public void newItemClick() {
        currentState = "newItem";
        activateUserInputElements();
        deleteStylesFromInputElements();
    }

    /**
     * Changes current state to allow the change to a selected item from
     * the tableview.
     */
    @FXML
    public void changeItemClick() {
        currentState = "changeItem";
        activateUserInputElements();
        deleteStylesFromInputElements();
        GroceryItem selectedItem = getSelectedItem();
        groceryCategoryComboBox.setValue(selectedItem.getGroceryCategory());
        groceryItemTextField.setText(selectedItem.toString());
    }

    /**
     * Delete button click handler. Deletes grocery items from database if the
     * item is not used in a recipe.
     */
    @FXML
    public void deleteItemClick() {
        GroceryItem selectedItem = getSelectedItem();
        try {
            DataHandler.deleteGroceryItem(selectedItem);
            ShoppingList.getInstance().deleteGroceryItem(selectedItem);
            enableEditingButtons();
        } catch (IllegalArgumentException e) {
            String message = "Löschen der Zutat nicht möglich. Es exsistie"
                    + "ren Rezepte, die diese Zutat benutzen";
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Computes if the user input of grocery item and category already exists.
     * If that's the case, the save button will be deactivated. Else: activated
     */
    @FXML
    public void onUserInput() {
        deleteStylesFromInputElements();
        errorTooltip.clearText();

        GroceryCategory categoryInput = groceryCategoryComboBox
                .getSelectionModel().getSelectedItem();
        String itemNameInput = groceryItemTextField.getText();

        if (checkItemCreationCondition(itemNameInput, categoryInput)) {
            grocerySaveButton.setDisable(false);
            groceryItemErrorLabel.setVisible(false);
        } else {
            grocerySaveButton.setDisable(true);
        }
    }

    /**
     * Handles a save click, responds to the current state --> saving an item.
     */
    public void handleSaveClick() {
        if (currentState.equals("newItem")) {
            createNewItem();
        } else if (currentState.equals("changeItem")) {
            changeItem();
        }

        enableEditingButtons();
        disableAllInputFields();
        errorTooltip.clearText();
    }

    /**
     * Creates a new grocery item.
     */
    private void createNewItem() {
        GroceryCategory relatedCategory = groceryCategoryComboBox.
                getSelectionModel().getSelectedItem();
        String newName = groceryItemTextField.getText();
        int id = DataHandler.saveNewGroceryItem(relatedCategory, newName);
        GroceryItem newItem = new GroceryItem(id, newName, relatedCategory);
        ShoppingList.getInstance().addGroceryItem(newItem);
        focusTableOnNewItem(newItem);
    }

    private void changeItem() {
        GroceryItem selectedItem = getSelectedItem();

        GroceryCategory inputCategory = groceryCategoryComboBox.
                getSelectionModel().getSelectedItem();
        String inputName = groceryItemTextField.getText();

        selectedItem.setName(inputName);
        selectedItem.setAffiliatedGroceryCategory(inputCategory);
        DataHandler.changeGroceryItem(selectedItem, inputName, inputCategory);
    }

    /**
     * Focuses the table view on the item.
     *
     * @param item item itself
     */
    public void focusTableOnNewItem(GroceryItem item) {
        groceryTable.getSelectionModel().select(item);
        groceryTable.getFocusModel().focus(
                groceryTable.getSelectionModel().getSelectedIndex());
        groceryTable.scrollTo(groceryTable.getSelectionModel().getSelectedIndex());
    }

    /**
     * Checks if everything is correct (no nulls or item name already exists),
     * so that the item can be created.
     *
     * @param newItemName name of the new item
     * @param category    category of the new item
     * @return true, if everything is alright | false else
     */
    public boolean checkItemCreationCondition(String newItemName,
                                              GroceryCategory category) {
        if (category == null) {
            groceryItemErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("Keine Kategorie ausgewählt");
            return false;
        }
        if (newItemName.length() == 0) {
            groceryItemErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("Name darf nicht leer sein!");
            return false;
        }
        if (ShoppingList.getInstance().isItemInList(newItemName, category)) {
            groceryItemErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("Zutat existiert bereits!");
            return false;
        }
        return true;
    }

    /**
     * Enables the change and delete item button when the user selects an item
     * from the tableview.
     */
    @FXML
    public void enableEditingButtons() {
        groceryChangeItemButton.setDisable(false);
        groceryDeleteItemButton.setDisable(false);

        GroceryItem selectedItem = getSelectedItem();
        groceryCategoryComboBox.setValue(selectedItem.getGroceryCategory());
        groceryItemTextField.setText(selectedItem.toString());

        groceryItemErrorLabel.setVisible(false);
    }

    /**
     * Deletes the styles from the user input elements.
     */
    private void deleteStylesFromInputElements() {
        groceryCategoryComboBox.setStyle("");
        groceryItemTextField.setStyle("");
    }

    /**
     * Disables all frontend input fields of the user
     */
    private void disableAllInputFields() {
        groceryCategoryComboBox.setDisable(true);
        groceryItemTextField.setDisable(true);
    }

    /**
     * Activates the category combobox and item text field for the user. Sets
     * the focus on the item textfield.
     */
    private void activateUserInputElements() {
        groceryCategoryComboBox.setDisable(false);
        groceryItemTextField.setDisable(false);
        groceryItemTextField.requestFocus();
    }

    /**
     * Represents extended Tooltip class for Error label when user makes faulty
     * inputs.
     */
    private class ErrorTooltip extends Tooltip {

        public ErrorTooltip() {
            this.setShowDelay(Duration.millis(300));
        }

        public void clearText() {
            this.setText("");
        }

        public void addErrorMessage(String newError) {
            String existingErrorMessage = this.getText();
            String newErrorMessage;
            if (existingErrorMessage.length() > 0) {
                newErrorMessage = existingErrorMessage + "\n" + newError;
            } else {
                newErrorMessage = newError;
            }
            this.setText(newErrorMessage);
        }

    }
}
