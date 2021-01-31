package frontend.recipetab;

import backend.data.RecipeHandler;
import backend.dataclasses.recipe.Recipe;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RecipeTabController {

    @FXML
    private Button recipeSaveButton; //Button for saving changes/new recipe
    @FXML
    private RecipeDetailsWidgetController recipeDetailsWidgetController;
    @FXML
    private PreparationStepWidgetController preparationStepWidgetController;
    @FXML
    private IngredientTableWidgetController ingredientTableWidgetController;
    @FXML
    private CommentWidgetController commentWidgetController;
    private Recipe currentRecipe;
    private BooleanProperty changeDetected;

    @FXML
    public void initialize() {
        changeDetected = new SimpleBooleanProperty(false);
    }

    /**
     * Binds the changeDetected property of this widget to the other recipe
     * editing widgets.
     */
    private void bindChangeDetected() {
        changeDetected.bind(recipeDetailsWidgetController.getChangeDetected()
                .or(preparationStepWidgetController.getChangeDetected())
                .or(commentWidgetController.getChangeDetected()));
    }

    /**
     * Enables the editing of the values of this widget components.
     */
    public void enableEdit() {
        commentWidgetController.enableEdit();
        recipeDetailsWidgetController.enableEdit();
        preparationStepWidgetController.enableEdit();

        bindChangeDetected();

        changeDetected.addListener((observableValue, oldBool, newBool) ->
                handleSaveButton(newBool));
    }

    /**
     * Handles the disable value of the save button.
     *
     * @param bool true for enabling save button | false for disabling button
     */
    public void handleSaveButton(boolean bool) {
        recipeSaveButton.setDisable(!bool);
    }

    /**
     * Disables the save button.
     */
    private void disableSaving() {
        recipeSaveButton.setDisable(true);
    }

    /**
     * Loads the data of the recipe into the tab.
     */
    public void loadRecipeDataIntoTab(Recipe selectedRecipe) {

        currentRecipe = selectedRecipe;
        recipeDetailsWidgetController.initializeRecipeDetails(currentRecipe);

        ingredientTableWidgetController.showIngredients(currentRecipe.getObservableIngredients());

        preparationStepWidgetController.initialize(currentRecipe.getPreparation());

        commentWidgetController.initializeComment(currentRecipe.getComment());

        enableEdit();
    }

    /**
     * Updates the recipe in the database.
     */
    public void saveChanges() {


        currentRecipe.setComment(commentWidgetController.getComment());

        recipeDetailsWidgetController.resetChangeDetected();

        RecipeHandler.updateRecipe(currentRecipe);
    }

}

