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
public class RecipeDetailsWidgetController extends RecipeWidgetsController {

    @FXML
    private TextField titleTextField; //name textfield for shown recipe
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
        titleTextField.setText(selectedRecipe.getTitle());
        initializeCategoryComboBox();
        setCategory(selectedRecipe);
        timeTextField.setText(Integer.toString(selectedRecipe.getTime()));
        initializeVegetarianCheckbox(selectedRecipe.isVegetarian());
        sourceTextField.setText(selectedRecipe.getSource());
        initializePortionsizeComboBox();
        setPortionsize(selectedRecipe);

        super.initialize();
    }

    /**
     * Initializes the category combobox.
     */
    private void initializeCategoryComboBox() {

        Set<String> categories = ListOfRecipeCategories.
                getInstance().getSavedRecipeCategories().stream().
                map(RecipeCategory::getName).collect(Collectors.toSet());
        categoryComboBox.getItems().addAll(categories);
    }

    /**
     * Displays the recipe category of the recipe in the combobox.
     *
     * @param recipe itself
     */
    private void setCategory(Recipe recipe) {
        try {
            String categoryName = recipe.getRecipeCategory();
            categoryComboBox.getSelectionModel().select(categoryName);
        } catch (NullPointerException e) {
        }
    }

    /**
     * Initializes the portionsize combobox.
     */
    private void initializePortionsizeComboBox() {
        portionsizeUnitComboBox.getItems().addAll(Portionsize.getPortionUnits());
    }

    /**
     * Displays the portionsize details of the recipe.
     *
     * @param recipe the recipe itself
     */
    private void setPortionsize(Recipe recipe) {
        try {
            Portionsize portion = recipe.getPortionsize();
            portionsizeUnitComboBox.getSelectionModel().
                    select(portion.getUnit());
            portionsizeAmountTextField.setText(
                    Double.toString(portion.getAmount()));
        } catch (NullPointerException e) {
        }
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
     * Sets the disable attribute of this widget components.
     *
     * @param bool false for enabling components | true for disabling
     */
    private void setDisableValueOfComponents(boolean bool) {
        titleTextField.setDisable(bool);
        categoryComboBox.setDisable(bool);
        timeTextField.setDisable(bool);
        vegetarianCheckBox.setDisable(bool);
        sourceTextField.setDisable(bool);
        portionsizeAmountTextField.setDisable(bool);
        portionsizeUnitComboBox.setDisable(bool);
    }

    public void onChange() {
        changeDetected.setValue(true);
    }

    public Recipe getRecipeDetatils() {
        Recipe recipe = new Recipe();
        recipe.setTitle(titleTextField.getText());
        RecipeCategory cat = ListOfRecipeCategories.getInstance()
                .getRecipeCategory(
                        categoryComboBox.getSelectionModel().getSelectedItem());
        recipe.setCategory(cat);
        recipe.setTime(Integer.parseInt(timeTextField.getText()));
        recipe.setVegetarian(vegetarianCheckBox.selectedProperty().get());
        recipe.setSource(sourceTextField.getText());

        double amount = Double.parseDouble(portionsizeAmountTextField.getText());
        Portionsize.PortionUnit unit = portionsizeUnitComboBox
                .getSelectionModel().getSelectedItem();
        recipe.setPortionsize(new Portionsize(amount, unit));

        return recipe;
    }

}
