package frontend;

import backend.dataclasses.recipe.Portionsize;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class WindowRecipeTabController {

    @FXML
    private Label recipeIDLabel; //ID label for shown recipe
    @FXML
    private TextField recipeNameTextField; //name textfield for shown recipe
    @FXML
    private ComboBox<String> recipeCategoryComboBox;
    @FXML
    private Button recipeSaveButton; //Button for saving changes/new recipe
    @FXML
    private Button recipeChangeButton; //Button for changing a recipe
    @FXML
    private Button recipeDeleteButton; //Button for deleting a recipe
    @FXML
    private TextField recipeTimeTextField; //Time textfield for shown recipe
    @FXML
    private CheckBox recipeVegetarianCheckbox; //Checkbox if recipe is vegetarian
    @FXML
    private TextField recipeSourceTextField; //textfield for shown recipe source
    @FXML
    private TextField recipePortionsizeAmountTextField;
    @FXML
    private ComboBox<Portionsize.PortionUnit> recipePortionsizeUnitComboBox;
    @FXML
    private TableView<Ingredient> recipeTabIngredientTable;
    @FXML
    private TableColumn<Ingredient, String> recipeTabIngredientCategoryColumn;
    @FXML
    private TableColumn<Ingredient, String> recipeTabIngredientItemColumn;
    @FXML
    private TableColumn<Ingredient, String> recipeTabIngredientQuantityColumn;

    /**
     * Activates the three buttons for a recipe: save, change, new.
     */
    private void activateRecipeButtons() {
        recipeSaveButton.setDisable(false);
        recipeChangeButton.setDisable(false);
        recipeDeleteButton.setDisable(false);
    }

    /**
     * Loads the data of the recipe into the tab.
     */
    void loadRecipeDataIntoTab(Recipe selectedRecipe) {

        recipeIDLabel.setText(Integer.toString(selectedRecipe.getID()));
        recipeNameTextField.setText(selectedRecipe.getTitle());

        Set<String> categories = ListOfRecipeCategories.
                getInstance().getSavedRecipeCategories().stream().
                map(RecipeCategory::getName).collect(Collectors.toSet());
        recipeCategoryComboBox.getItems().addAll(categories);
        recipeCategoryComboBox.getSelectionModel().
                select(selectedRecipe.getRecipeCategory());


        recipeTimeTextField.setText(Integer.toString(selectedRecipe.getTime()));

        Portionsize portion = selectedRecipe.getPortionsize();
        recipePortionsizeAmountTextField.setText(Double.toString(portion.getAmount()));
        recipePortionsizeUnitComboBox.getItems().addAll(Portionsize.getPortionUnits());
        recipePortionsizeUnitComboBox.getSelectionModel().
                select(portion.getUnit());

        if (selectedRecipe.isVegetarian()) {
            recipeVegetarianCheckbox.setSelected(true);
        } else {
            recipeVegetarianCheckbox.setSelected(false);
        }
        recipeSourceTextField.setText(selectedRecipe.getSource());

        recipeTabIngredientCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryString"));
        recipeTabIngredientItemColumn.setCellValueFactory(new PropertyValueFactory<>("itemString"));
        recipeTabIngredientQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        recipeTabIngredientTable.setItems(selectedRecipe.getObservableIngredients());
    }

}

