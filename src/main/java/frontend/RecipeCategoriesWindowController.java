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
    @FXML
    private Label recipeCategoryErrorLabel;
    /**
     * Represents last button click, can be: "new", "change".
     */
    private String currentState = "";
    /**
     * Represents tooltip for showing user specific errors on his input.
     */
    private ErrorTooltip errorTooltip;

    /**
     * Initializer for RecipeCategoriesWindow.fxml. Sets up all necessary data
     * in frontend, e.g. loads data into table, svg into frontend components.
     */
    @FXML
    protected void initialize() {
        loadDataIntoCategoryTable();
        recipeCategoryTable.getSortOrder().add(recipeCategoryTableNameColumn);
        errorTooltip = new ErrorTooltip();
        recipeCategoryErrorLabel.setTooltip(errorTooltip);
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
        recipeCategoryIDTextField.setDisable(false);
        deleteStylesFromTextfields();
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
        deleteStylesFromTextfields();
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
     * Computes if id and category name already exists when the user types in
     * the textfields.
     */
    public void onUserInput() {

        clearTextFieldStyle(recipeCategoryIDTextField);
        clearTextFieldStyle(recipeCategoryNameTextField);
        errorTooltip.clearText();

        String id = recipeCategoryIDTextField.getText();
        String categoryName = recipeCategoryNameTextField.getText();

        if (currentState.equals("change")) {
            boolean noEmptyStrings = checkForEmptyStrings(id, categoryName);
            if (noEmptyStrings) {
                checkForJustAUserChange(id, categoryName);
            } else {
                recipeCategorySaveButton.setDisable(true);
            }
        } else {
            boolean everythingOkay;  //no double existence or empty strings

            boolean noDoubleExistence = checkForDoubleExistence(id, categoryName);
            boolean noEmptyStrings = checkForEmptyStrings(id, categoryName);
            everythingOkay = noDoubleExistence && noEmptyStrings;

            if (everythingOkay) {
                recipeCategorySaveButton.setDisable(false);
                recipeCategoryErrorLabel.setVisible(false);
            } else {
                recipeCategorySaveButton.setDisable(true);
            }
        }
    }

    /**
     * Checks if id and recipe category name already exist in the system. Colors
     * the frontend elements if the user typed in an already existing one.
     *
     * @param id           id from user input
     * @param categoryName category name from user input
     * @return true, if id and categoryName don't exist already | false, else
     */
    private boolean checkForDoubleExistence(String id, String categoryName) {

        boolean noDoubleExistence = true;
        if (!ListOfRecipeCategories.getInstance().isIDNonExistent(id)) {
            colorTextFieldInErrorColor(recipeCategoryIDTextField);
            noDoubleExistence = false;

            recipeCategoryErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("ID darf nicht doppelt "
                    + "vorkommen!");
        }

        if (!ListOfRecipeCategories.getInstance()
                .isCategoryNameNonExistent(categoryName)) {
            colorTextFieldInErrorColor(recipeCategoryNameTextField);
            noDoubleExistence = false;

            recipeCategoryErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("Name darf nicht doppelt "
                    + "vorkommen!");
        }
        return noDoubleExistence;
    }

    /**
     * Checks if id and recipe category name are empty strings. If so the
     * Textfields will be colored to show an error.
     *
     * @param id           id from user input
     * @param categoryName category name from user input
     * @return true, if id and categoryName not empty | false, else
     */
    private boolean checkForEmptyStrings(String id, String categoryName) {

        boolean noEmptyStrings = true;
        if (id.length() == 0) {
            colorTextFieldInErrorColor(recipeCategoryIDTextField);
            noEmptyStrings = false;

            recipeCategoryErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("ID darf nicht leer sein!");
        }

        if (categoryName.length() == 0) {
            colorTextFieldInErrorColor(recipeCategoryNameTextField);
            noEmptyStrings = false;

            recipeCategoryErrorLabel.setVisible(true);
            errorTooltip.addErrorMessage("Name darf nicht leer sein!");
        }
        return noEmptyStrings;
    }

    /**
     * Checks if user input is only a change of a category. In that case it is
     * allowed that the id or name already exists in the program. Will enable
     * the change button. Disable it otherwise.
     *
     * @param id           id from user input
     * @param categoryName category name from user input
     */
    private void checkForJustAUserChange(String id, String categoryName) {

        RecipeCategory selectedCategory = getSelectedCategory();
        String oldID = selectedCategory.getId();
        String oldName = selectedCategory.getName();

        ListOfRecipeCategories categoriesList = ListOfRecipeCategories
                .getInstance();

        boolean changeOnId = !oldID.equals(id);
        boolean changeOnName = !oldName.equals(categoryName);

        if (changeOnId && !changeOnName) {
            boolean correctChangeOnID = categoriesList.isIDNonExistent(id);
            if (correctChangeOnID) {
                recipeCategorySaveButton.setDisable(false);
                recipeCategoryErrorLabel.setVisible(false);
            } else {
                colorTextFieldInErrorColor(recipeCategoryIDTextField);
                recipeCategorySaveButton.setDisable(true);

                recipeCategoryErrorLabel.setVisible(true);
                errorTooltip.addErrorMessage("ID darf nicht doppelt "
                        + "vorkommen!");
            }
        } else if (!changeOnId && changeOnName) {
            boolean correctChangeOnName = categoriesList
                    .isCategoryNameNonExistent(categoryName);
            if (correctChangeOnName) {
                recipeCategorySaveButton.setDisable(false);
                recipeCategoryErrorLabel.setVisible(false);
            } else {
                colorTextFieldInErrorColor(recipeCategoryNameTextField);
                recipeCategorySaveButton.setDisable(true);

                recipeCategoryErrorLabel.setVisible(true);
                errorTooltip.addErrorMessage("Name darf nicht doppelt "
                        + "vorkommen!");
            }
        } else if (changeOnId && changeOnName) {
            boolean correctChangeOnID = categoriesList.isIDNonExistent(id);
            boolean correctChangeOnName = categoriesList
                    .isCategoryNameNonExistent(categoryName);
            if (correctChangeOnName && correctChangeOnID) {
                recipeCategorySaveButton.setDisable(false);
                recipeCategoryErrorLabel.setVisible(false);
            } else {
                if (!correctChangeOnID) {
                    colorTextFieldInErrorColor(recipeCategoryIDTextField);

                    errorTooltip.addErrorMessage("ID darf nicht doppelt "
                            + "vorkommen!");
                }
                if (!correctChangeOnName) {
                    colorTextFieldInErrorColor(recipeCategoryNameTextField);

                    errorTooltip.addErrorMessage("Name darf nicht doppelt "
                            + "vorkommen!");
                }
                recipeCategorySaveButton.setDisable(true);
                recipeCategoryErrorLabel.setVisible(true);
            }
        } else {    //basically the input completely equals the old category
            recipeCategorySaveButton.setDisable(false);
            recipeCategoryErrorLabel.setVisible(false);
        }
    }

    /**
     * Changes style of textfield to an error style.
     *
     * @param textField textfield in which the error occurred
     */
    private void colorTextFieldInErrorColor(TextField textField) {
        textField.setStyle("-fx-background-color: #ffdddc; -fx-border-color: grey");
    }

    /**
     * Clears the Style of the given textfield.
     *
     * @param textField textfield of which the style shall be removed
     */
    private void clearTextFieldStyle(TextField textField) {
        textField.setStyle("");
    }

    /**
     * Save button click handler. Saves the user input/changes into/to the
     * database/objects.
     */
    @FXML
    public void saveButtonClick() {
        if (currentState.equals("new")) {
            createNewCategory();
        } else if (currentState.equals("change")) {
            changeCategory();
        }
        recipeCategoryIDTextField.clear();
        recipeCategoryNameTextField.clear();
        recipeCategorySaveButton.setDisable(true);
    }

    /**
     * Deletes the styles of the 2 textfields : recipeCategoryIDTextField,
     * recipeCategoryNameTextField
     */
    private void deleteStylesFromTextfields() {
        recipeCategoryIDTextField.setStyle("");
        recipeCategoryNameTextField.setStyle("");
    }

    /**
     * Saves the new recipe category to the database and creates an object of it.
     */
    @FXML
    public void createNewCategory() {
        String newCatName = recipeCategoryNameTextField.getText();
        String newID = recipeCategoryIDTextField.getText();
        RecipeCategory recipeCategory = new RecipeCategory(newID, newCatName);
        ListOfRecipeCategories.getInstance().addRecipeCategory(recipeCategory);
        DataHandler.saveNewRecipeCategory(recipeCategory);
    }

    /**
     * Changes id/name of recipe category in database/program to user inputs.
     */
    @FXML
    private void changeCategory() {

        RecipeCategory selectedCategory = getSelectedCategory();
        String newID = recipeCategoryIDTextField.getText();
        String newName = recipeCategoryNameTextField.getText();

        DataHandler.updateRecipeCategory(selectedCategory, newID, newName);
    }


    /**
     * Enables the change category and delete Category buttons.
     */
    @FXML
    public void enableChangeButton() {
        recipeCategoryChangeButton.setDisable(false);
        recipeCategoryDeleteButton.setDisable(false);

        RecipeCategory selectedCategory = getSelectedCategory();
        recipeCategoryIDTextField.setText(selectedCategory.getId());
        recipeCategoryNameTextField.setText(selectedCategory.getName());
    }

}
