package frontend.recipetab;

import backend.dataclasses.recipe.Portionsize;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for widget that shows most of the recipe details to the user.
 */
public class RecipeDetailsWidgetController {

    @FXML
    private TextField nameTextField; //name textfield for shown recipe
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField timeTextField; //Time textfield for shown recipe
    @FXML
    private CheckBox vegetarianCheckBox; //Checkbox if recipe is vegetarian
    @FXML
    private TextField sourceTextField; //textfield for shown recipe source
    @FXML
    private TextField portionsizeAmountTextField;
    @FXML
    private ComboBox<Portionsize.PortionUnit> portionsizeUnitComboBox;

    /**
     * Displays the details of the selected recipe.
     *
     * @param selectedRecipe selected recipe
     */
    public void initializeRecipeDetails(Recipe selectedRecipe) {

        nameTextField.setText(selectedRecipe.getTitle());
        initializeCategory(selectedRecipe.getRecipeCategory());
        timeTextField.setText(Integer.toString(selectedRecipe.getTime()));
        initializeVegetarianCheckbox(selectedRecipe.isVegetarian());
        sourceTextField.setText(selectedRecipe.getSource());
        initializePortionsize(selectedRecipe.getPortionsize());
    }

    /**
     * Displays the recipe category of the recipe
     *
     * @param categoryName name of the recipe category
     */
    private void initializeCategory(String categoryName) {

        Set<String> categories = ListOfRecipeCategories.
                getInstance().getSavedRecipeCategories().stream().
                map(RecipeCategory::getName).collect(Collectors.toSet());
        categoryComboBox.getItems().addAll(categories);
        categoryComboBox.getSelectionModel().select(categoryName);
    }

    /**
     * Displays the portionsize details of the recipe
     *
     * @param portion portionsize of recipe
     */
    private void initializePortionsize(Portionsize portion) {

        portionsizeAmountTextField.setText(Double.toString(portion.getAmount()));
        portionsizeUnitComboBox.getItems().addAll(Portionsize.getPortionUnits());
        portionsizeUnitComboBox.getSelectionModel().
                select(portion.getUnit());
    }

    /**
     * Displays the vegetarian value of the recipe.
     */
    private void initializeVegetarianCheckbox(boolean vegetarian) {
        if (vegetarian) {
            vegetarianCheckBox.setSelected(true);
        } else {
            vegetarianCheckBox.setSelected(false);
        }

    }

    /**
     * Enables the editing of the values of this widget components.
     */
    public void enableEdit() {
        setDisableValueOfComponents(false);
    }

    /**
     * Disables the editing of the values of this widget components.
     */
    public void disableEdit() {
        setDisableValueOfComponents(true);
    }

    /**
     * Sets the disable attribute of this widget components.
     *
     * @param bool false for enabling components | true for disabling
     */
    private void setDisableValueOfComponents(boolean bool) {
        nameTextField.setDisable(bool);
        categoryComboBox.setDisable(bool);
        timeTextField.setDisable(bool);
        vegetarianCheckBox.setDisable(bool);
        sourceTextField.setDisable(bool);
        portionsizeAmountTextField.setDisable(bool);
        portionsizeUnitComboBox.setDisable(bool);
    }
}
