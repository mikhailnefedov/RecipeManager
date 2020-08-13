package frontend;

import backend.data.RecipeCategoryWriter;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;

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
    /**
     * Represents last button click, can be: "new", "change".
     */
    private String currentState = "";

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
        try {
            RecipeCategoryWriter.removeCategory(selectedCategory.getId(), selectedCategory.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //TODO: Handle Recipes with the deleted categories so that they get a dummy id
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
            try {
                RecipeCategoryWriter.writeNewCategory(newID, newCatName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            //TODO: Styling of Error
        }

        //TODO: Error Handling if Category already exists
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
            try {
                selectedCategory.setId(newID);
                selectedCategory.setName(newName);
                RecipeCategoryWriter.changeCategory(oldID, oldName, newID, newName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            //TODO: Throw error if category or id already exists.
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

    //TODO: Implement State Structure for Textfields
    //TODO: Rework newCategory System
}
