package frontend;

import backend.data.DataHandler;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RecipeCategoriesWindowController {

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
    @FXML
    public Label recipeCategoryCheckLabel;
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

    private void loadDataIntoCategoryTable() {
        recipeCategoryTableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        recipeCategoryTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        recipeCategoryTable.setItems(getCategories());
    }

    private ObservableList<RecipeCategory> getCategories() {
        return ListOfRecipeCategories.getInstance().getSavedRecipeCategories();
    }

    @FXML
    public void newCategoryClick() {
        currentState = "new";
        recipeCategoryNameTextField.setDisable(false);
        recipeCategoryNameTextField.requestFocus();
    }

    @FXML
    public void changeCategoryClick() {
        currentState = "change";
        RecipeCategory selectedCategory = recipeCategoryTable.getSelectionModel().getSelectedItem();
        recipeCategoryIDTextField.setDisable(false);
        recipeCategoryIDTextField.setText(selectedCategory.getId());
        recipeCategoryNameTextField.setDisable(false);
        recipeCategoryNameTextField.setText(selectedCategory.getName());
        recipeCategoryNameTextField.requestFocus();
    }

    /**
     * Delete Click handler. Deletes Category from data.
     */
    @FXML
    public void deleteCategoryClick() {
        RecipeCategory selectedCategory = recipeCategoryTable.getSelectionModel().getSelectedItem();
        getCategories().remove(selectedCategory);
        DataHandler.deleteRecipeCategory(selectedCategory.getId());
    }

    @FXML
    public void enterPressedOnTextFields() {
        if (currentState.equals("new")) {
            createNewCategory();
        } else if (currentState.equals("change")) {
            changeCategory();
        }
    }

    @FXML
    public void createNewCategory() {
        String newCatName = recipeCategoryNameTextField.getText();

        ListOfRecipeCategories listOfCategories = ListOfRecipeCategories.getInstance();
        try {
            String newID = listOfCategories.computeIDForRecipeCategory(newCatName);
            recipeCategoryIDTextField.setText(newID);
            listOfCategories.addRecipeCategory(newCatName);
            DataHandler.saveNewRecipeCategory(newID, newCatName);
        } catch (IllegalArgumentException e) {
            System.out.println("Category already exists");
        }

    }

    @FXML
    public void updateNewShownID() {
        if (currentState.equals("new")) {
            String newCategoryName = recipeCategoryNameTextField.getText();
            String newID = ListOfRecipeCategories.getInstance().computeIDForRecipeCategory(newCategoryName);
            recipeCategoryIDTextField.setText(newID);
        }
    }

    @FXML
    public void changeCategory() {

        RecipeCategory selectedCategory = recipeCategoryTable.getSelectionModel().getSelectedItem();
        String oldID = selectedCategory.getId();
        String oldName = selectedCategory.getName();
        String newID = recipeCategoryIDTextField.getText();
        String newName = recipeCategoryNameTextField.getText();

        if (checkChangeCondition(oldID, oldName, newID, newName)) {
            selectedCategory.setId(newID);
            selectedCategory.setName(newName);
            DataHandler.changeRecipeCategory(oldID, newID, newName);
        } else {
            System.out.println("Category already exists");
        }
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
     * Enables the Change category and Delete Category buttons.
     */
    @FXML
    public void enableChangeButton() {
        recipeCategoryChangeButton.setDisable(false);
        recipeCategoryDeleteButton.setDisable(false);
    }
}
