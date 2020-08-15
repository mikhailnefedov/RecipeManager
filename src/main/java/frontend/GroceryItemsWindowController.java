package frontend;

import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GroceryItemsWindowController {

    @FXML
    private TableView<GroceryItem> groceryTable;
    @FXML
    private TableColumn<GroceryItem, String> groceryTableCategoryColumn;
    @FXML
    private TableColumn<GroceryItem, String> groceryTableItemColumn;

    @FXML
    protected void initialize() {
        loadDataIntoCategoryTable();
        groceryTable.getSortOrder().add(groceryTableCategoryColumn);
    }

    private void loadDataIntoCategoryTable() {
        groceryTableCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("affiliatedCategory"));
        groceryTableItemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        groceryTable.setItems(getItems());
    }

    private ObservableList<GroceryItem> getItems() {
        return ShoppingList.getInstance().getObservableItems();
    }
}
