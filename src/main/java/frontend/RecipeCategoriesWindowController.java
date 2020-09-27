package frontend;

import backend.data.DataHandler;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecipeCategoriesWindowController {

    @FXML
    public Label recipeCategoryCheckLabel;
    @FXML
    private TableView<RecipeCategory> recipeCategoryTable;
    @FXML
    private TableColumn<RecipeCategory, String> recipeCategoryTableIDColumn;
    @FXML
    private TableColumn<RecipeCategory, String> recipeCategoryTableNameColumn;
    @FXML
    private TextField recipeCategoryIDTextField;
    @FXML
    private TextField recipeCategoryNameTextField;
    @FXML
    private Button recipeCategoryChangeButton;
    @FXML
    private Button recipeCategoryDeleteButton;
    @FXML
    private Button recipeCategorySaveButton;
    /**
     * Represents last button click, can be: "new", "change".
     */
    private String currentState = "";

    /**
     * Initializer for RecipeCategoriesWindow.fxml. Sets up all necessary data
     * in frontend, e.g. loads data into table, svg into frontend components.
     */
    @FXML
    protected void initialize() {
        loadDataIntoCategoryTable();
        recipeCategoryTable.getSortOrder().add(recipeCategoryTableNameColumn);
    }

    /**
     * @return Observable list of the saved recipe categories.
     */
    private ObservableList<RecipeCategory> getCategories() {
        return ListOfRecipeCategories.getInstance().getSavedRecipeCategories();
    }

    /**
     * Loads the recipe categories into the table.
     */
    private void loadDataIntoCategoryTable() {
        recipeCategoryTableIDColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        recipeCategoryTableNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        recipeCategoryTable.setItems(getCategories());
    }

    /**
     * Activates the textfield for the recipe category name and focuses the
     * mouse on it.
     */
    private void activateNameTextfield() {
        recipeCategoryNameTextField.setDisable(false);
        recipeCategoryNameTextField.requestFocus();
    }

    /**
     * Used for click on new category button. Changes frontend elements to
     * allow user inputs.
     */
    @FXML
    public void newCategoryClick() {
        currentState = "new";       //Change currentState for "new" state
        activateNameTextfield();
    }

    /**
     * @return selected recipe category from frontend table view
     * (recipeCategoryTable).
     */
    private RecipeCategory getSelectedCategory() {
        return recipeCategoryTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Used for click on change category button. Changes frontend elements to
     * allow user inputs.
     */
    @FXML
    public void changeCategoryClick() {
        currentState = "change";    //Change currentState for "change" state
        RecipeCategory selectedCategory = getSelectedCategory();
        activateNameTextfield();
        recipeCategoryNameTextField.setText(selectedCategory.getName());
        recipeCategoryIDTextField.setDisable(false);
        recipeCategoryIDTextField.setText(selectedCategory.getId());
    }

    /**
     * Delete button click handler. Deletes recipe category from data and
     * database.
     */
    @FXML
    public void deleteCategoryClick() {
        RecipeCategory selectedCategory = getSelectedCategory();
        try {
            DataHandler.deleteRecipeCategory(selectedCategory);
            getCategories().remove(selectedCategory);
        } catch (IllegalArgumentException e) {
            String message = "Löschen der Kategorie nicht möglich. Es exsistie"
                    + "ren Rezepte, die dieser Kategorie zugewiesen sind";
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Save button click handler. Saves the user input/changes into/to the
     * database/objects.
     */
    @FXML
    public void saveButtonClick() {
        if (currentState.equals("new")) {
            //createNewCategory();
        } else if (currentState.equals("change")) {
            changeCategory();
        }
    }

    //ListOfRecipeCategories listOfCategories = ListOfRecipeCategories.getInstance();
    //used for computing id String newID = listOfCategories.computeIDForRecipeCategory(newCatName);

    @FXML
    public void createNewCategory() {
        String newCatName = recipeCategoryNameTextField.getText();
        String newID = recipeCategoryIDTextField.getId();
        //TODO : change to add recipecategory(id, catname)
        //listOfCategories.addRecipeCategory(newCatName);
        DataHandler.saveNewRecipeCategory(newID, newCatName);
    }

    @FXML
    public void updateNewShownID() {
        if (currentState.equals("new")) {
            String newCategoryName = recipeCategoryNameTextField.getText();
            String newID = ListOfRecipeCategories.getInstance().computeIDForRecipeCategory(newCategoryName);
            recipeCategoryIDTextField.setText(newID);
        }
    }

    /**
     * Changes id/name of recipe category in database/program to user inputs.
     */
    @FXML
    private void changeCategory() {

        RecipeCategory selectedCategory = getSelectedCategory();
        String oldID = selectedCategory.getId();
        String newID = recipeCategoryIDTextField.getText();
        String newName = recipeCategoryNameTextField.getText();

        selectedCategory.setId(newID);
        selectedCategory.setName(newName);
        DataHandler.changeRecipeCategory(oldID, newID, newName);
    }

    /**
     * Checks Change conditions.
     *
     * @return true, if newID or newName don't exist | false, else
     */
    private boolean checkChangeCondition(String oldID, String oldName,
                                         String newID, String newName) {
        ListOfRecipeCategories listOfCategories = ListOfRecipeCategories.getInstance();
        //Change only to the id --> Check if ID already exists
        if (!newID.equals(oldID) && newName.equals(oldName) &&
                listOfCategories.isIDNonExistent(newID)) {
            return true;
        }
        //Change only to the name --> Check if Name already exists
        if (newID.equals(oldID) && !newName.equals(oldName) &&
                listOfCategories.isCategoryNameNonExistent(newName)) {
            return true;
        }
        //Change to id and name --> Check if name and id already exist
        return !newID.equals(oldID) && !newName.equals(oldName) &&
                listOfCategories.isIDNonExistent(newID) &&
                listOfCategories.isCategoryNameNonExistent(newName);
        //else: false
    }

    /**
     * Enables the change category and delete Category buttons.
     */
    @FXML
    public void enableChangeButton() {
        recipeCategoryChangeButton.setDisable(false);
        recipeCategoryDeleteButton.setDisable(false);
    }

    /**
     * Disables the change category and delete category buttons.
     */
    private void DisableChangeButton() {
        recipeCategoryChangeButton.setDisable(true);
        recipeCategoryDeleteButton.setDisable(true);
    }
}
