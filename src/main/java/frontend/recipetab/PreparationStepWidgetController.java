package frontend.recipetab;

import backend.dataclasses.recipe.PreparationStep;
import frontend.helper.WindowLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class PreparationStepWidgetController extends RecipeWidgetsController {
    @FXML
    private TextArea instructionTextArea;
    @FXML
    private Button upButton;
    @FXML
    private Button editButton;
    @FXML
    private Button downButton;
    private InstructionWidgetController instructionEditWidgetController;
    private IntegerProperty currentStep;
    private SimpleListProperty<PreparationStep> preparationSteps;

    /**
     * Enables the editing of the values of this widget components.
     */
    public void enableEdit() {
        editButton.setDisable(false);
    }

    /**
     * Initializes the controller for a new recipe. Initializes the GUI of the
     * widget.
     *
     * @param preparationSteps list of preparation steps of the recipe.
     */
    public void initialize(ObservableList<PreparationStep> preparationSteps) {
        super.initialize();
        this.preparationSteps = new SimpleListProperty<>();
        this.preparationSteps.setValue(preparationSteps);
        preparationSteps.addListener((ListChangeListener<? super PreparationStep>) change -> {
            changeDetected.setValue(true);
            bindButtons();
            bindTextArea();
        });

        currentStep = new SimpleIntegerProperty(0);
        currentStep.addListener(observable -> bindTextArea());

        bindButtons();
        bindTextArea();
    }

    /**
     * Binds the up and down button disable property, so that button is
     * disabled if there is no next/previous instruction available.
     */
    public void bindButtons() {
        upButton.disableProperty().bind(preparationSteps.isNull()
                .or(preparationSteps.emptyProperty())
                .or(currentStep.isEqualTo(0)));

        downButton.disableProperty().bind(preparationSteps.isNull()
                .or(preparationSteps.emptyProperty())
                .or(currentStep.isEqualTo(preparationSteps.size() - 1)));
    }

    /**
     * Binds the text area text property to the current instruction.
     */
    private void bindTextArea() {
        instructionTextArea.textProperty().unbind();
        instructionTextArea.setText("");
        if (preparationSteps.size() > 0) {
            instructionTextArea.textProperty().bind(preparationSteps
                    .get(currentStep.getValue()).getInstructionProperty());
        }
    }

    /**
     * Increments currentStep value by 1.
     */
    public void nextStep() {
        currentStep.setValue(currentStep.getValue() + 1);
    }

    /**
     * Decrements currentStep value by 1.
     */
    public void previousStep() {
        currentStep.setValue(currentStep.getValue() - 1);
    }

    /**
     * Opens a new window for editing the instructions.
     *
     * @throws IOException failed or interrupted I/O operations
     */
    public void openEditWindow() throws IOException {
        instructionEditWidgetController = (InstructionWidgetController)
                WindowLoader.openNewWindowReturnController(
                        "recipetab/InstructionWidget",
                        "Editiere Anleitung");
        instructionEditWidgetController.initializeInstructions(preparationSteps);
    }

}