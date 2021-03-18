package frontend.recipetab;

import backend.data.RecipeHandler;
import backend.dataclasses.recipe.PreparationStep;
import backend.dataclasses.recipe.Recipe;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
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
                .or(ingredientTableWidgetController.getChangeDetected())
                .or(preparationStepWidgetController.getChangeDetected())
                .or(commentWidgetController.getChangeDetected()));
    }

    /**
     * Activates/Deactivates the save button when changeDetected changes.
     */
    public void addListenerToChangeDetected() {
        changeDetected.addListener((observableValue, oldBool, newBool) ->
                recipeSaveButton.setDisable(!newBool));
    }

    /**
     * Enables the editing of the values of this widget components.
     */
    public void enableEdit() {
        recipeDetailsWidgetController.enableEdit();
        ingredientTableWidgetController.enableEdit();
        preparationStepWidgetController.enableEdit();
        commentWidgetController.enableEdit();

        bindChangeDetected();
        addListenerToChangeDetected();
    }

    /**
     * Loads the data of the recipe into the tab.
     */
    public void loadRecipeDataIntoTab(Recipe selectedRecipe) {

        currentRecipe = selectedRecipe;
        recipeDetailsWidgetController.initializeRecipeDetails(currentRecipe);

        ingredientTableWidgetController.showIngredients(currentRecipe);

        preparationStepWidgetController.initialize(currentRecipe.getObservablePreparation());

        commentWidgetController.initializeComment(currentRecipe.getComment());

        enableEdit();
    }

    /**
     * Updates the recipe in the database.
     */
    public void saveChanges() {

        saveOrUpdateRecipe();
        resetChangeDetected();
    }

    /**
     * Saves the comment if user changed it.
     */
    private void saveOrUpdateRecipe() {

        Recipe newRecipeData = recipeDetailsWidgetController.getRecipeDetatils();

        ingredientTableWidgetController.saveChanges();

        if (preparationStepWidgetController.getChangeDetected().get()) {
            ObservableList<PreparationStep> preparationSteps =
                    preparationStepWidgetController.getPreparation();
            currentRecipe.setPreparation(preparationSteps);
        }

        newRecipeData.setComment(commentWidgetController.getComment());

        RecipeHandler.saveOrUpdateRecipe(currentRecipe, newRecipeData);
    }


    /**
     * Sets the changeDetected property of the children (controllers) to false.
     */
    private void resetChangeDetected() {
        recipeDetailsWidgetController.resetChangeDetected();
        ingredientTableWidgetController.resetChangeDetected();
        preparationStepWidgetController.resetChangeDetected();
        commentWidgetController.resetChangeDetected();
    }

}

