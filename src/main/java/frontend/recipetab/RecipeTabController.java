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
    private Button recipeChangeButton; //Button for changing a recipe
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
    public void loadRecipeDataIntoTab(Recipe selectedRecipe) {

        currentRecipe = selectedRecipe;
        recipeDetailsWidgetController.initializeRecipeDetails(currentRecipe);

        ingredientTableWidgetController.showIngredients(currentRecipe.getObservableIngredients());

        recipeCommentTextArea.setText(currentRecipe.getComment());

        preparationStepWidgetController.initialize(currentRecipe.getPreparation());
    }

    public void saveChanges() {
        RecipeHandler.updateRecipe(currentRecipe);
    }

}

