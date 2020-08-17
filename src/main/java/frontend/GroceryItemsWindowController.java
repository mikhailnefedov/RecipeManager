package frontend;

import backend.data.DataHandler;
import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    /**
     * String representing the current state of input. Values: "newItem"
     */
    private String currentState="";

    /**
     * Initializes window. Loads necessary data into table view and sets other
     * view specific things.
     */
    @FXML
    protected void initialize() {
        loadItemsIntoCategoryTable();
        groceryTable.getSortOrder().add(groceryTableCategoryColumn);
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
     * @return list of every grocery item
     */
    private ObservableList<GroceryItem> getGroceryItems() {
        return ShoppingList.getInstance().getObservableItems();
    }

    /**
     * Handler for a click on the new item button. Enables Textfield and
     * Combobox for creating a new grocery item.
     */
    public void handleNewItemClick() {
        currentState = "newItem";
        groceryCategoryComboBox.setDisable(false);
        groceryItemTextField.setDisable(false);

        groceryCategoryComboBox.getItems().addAll(
                ShoppingList.getInstance().getGroceryCategories());
    }

    /**
     * Handles a save click, responds to the current state --> saving an item.
     */
    public void handleSaveClick() {
        if (currentState.equals("newItem")) {
            createNewItem();
        }

        disableAllInputFields();
    }

    /**
     * Creates a new grocery item.
     */
    public void createNewItem() {
        GroceryCategory selectedCategory = groceryCategoryComboBox.
                getSelectionModel().getSelectedItem();
        String newName = groceryItemTextField.getText();

        if (checkItemCreationCondition(newName, selectedCategory)) {
            GroceryItem newItem = new GroceryItem(newName, selectedCategory);

            ShoppingList.getInstance().addGroceryItem(newItem);
            DataHandler.saveNewGroceryItem(newItem);
            //TODO: sorting table after creating item

            focusTableOnNewItem(newItem);
        } else {
            //TODO: Frontend visualization of error
            System.out.println("Error when creating grocery item");
        }


    }

    /**
     * Focuses the table view on the item.
     * @param item item
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
     * @param category category of the new item
     * @return true, if everything is alright | false else
     */
    public boolean checkItemCreationCondition(String newItemName,
                                              GroceryCategory category) {
        if (newItemName.length() == 0 || category == null) {
            //Frontend visualization needs to be here
            return false;
        }
        if (ShoppingList.getInstance().isItemInList(newItemName, category)) {
            //Frontend visualization needs to be here
            return false;
        }
        return true;
    }

    /**
     * Disables all frontend input fields of the user
     */
    public void disableAllInputFields() {
        groceryCategoryComboBox.setDisable(true);
        groceryItemTextField.setDisable(true);
    }
}
