package frontend.recipetab;

import backend.data.RecipeHandler;
import backend.dataclasses.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RecipeTabController {

    @FXML
    private Button recipeSaveButton; //Button for saving changes/new recipe
    @FXML
    private Button recipeEditButton; //Button for changing a recipe
    @FXML
    private Button recipeDeleteButton; //Button for deleting a recipe
    @FXML
    private TextArea recipeCommentTextArea;
    @FXML
    private RecipeDetailsWidgetController recipeDetailsWidgetController;
    @FXML
    private PreparationStepWidgetController preparationStepWidgetController;
    @FXML
    private IngredientTableWidgetController ingredientTableWidgetController;
    private Recipe currentRecipe;

    /**
     * Enables the editing of the values of this widget components.
     */
    public void enableEdit() {
        recipeCommentTextArea.setDisable(false);
        recipeDetailsWidgetController.enableEdit();
        preparationStepWidgetController.enableEdit();
    }

    /**
     * Enables the edit and delete button of this widget.
     */
    private void enableChangeButtons() {
        recipeEditButton.setDisable(false);
        recipeDeleteButton.setDisable(false);
    }

    /**
     * Loads the data of the recipe into the tab.
     */
    public void loadRecipeDataIntoTab(Recipe selectedRecipe) {

        currentRecipe = selectedRecipe;
        recipeDetailsWidgetController.initializeRecipeDetails(currentRecipe);

        ingredientTableWidgetController.showIngredients(currentRecipe.getObservableIngredients());

        recipeCommentTextArea.setText(currentRecipe.getComment());

        preparationStepWidgetController.initialize(currentRecipe.getPreparation());

        enableChangeButtons();
    }

    public void saveChanges() {
        RecipeHandler.updateRecipe(currentRecipe);

        disableEdit();
    }

    /**
     * Disables the editing of the values of this widget components.
     */
    private void disableEdit() {
        recipeCommentTextArea.setDisable(true);
        recipeDetailsWidgetController.disableEdit();
        preparationStepWidgetController.disableEdit();
    }

}

