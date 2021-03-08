package frontend.recipetab;

import backend.dataclasses.recipe.PreparationStep;
import frontend.helper.WindowLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;

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
    private List<PreparationStep> preparationSteps;

    /**
     * Enables the editing of the values of this widget components.
     */
    public void enableEdit() {
        editButton.setDisable(false);
    }

    /**
     * Disables the editing of the values of this widget components.
     */
    public void disableEdit() {
        editButton.setDisable(true);
    }

    /**
     * Initializes the controller for a new recipe. Initializes the GUI of the
     * widget.
     *
     * @param preparationSteps list of preparation steps of the recipe.
     */
    public void initialize(List<PreparationStep> preparationSteps) {
        super.initialize();
        this.preparationSteps = preparationSteps;
        currentStep = new SimpleIntegerProperty(0);
        currentStep.addListener(observable -> updateButtons());
        currentStep.addListener(observable -> updateTextArea());

        if (preparationSteps == null || preparationSteps.size() == 0) {
            upButton.setDisable(true);
            downButton.setDisable(true);
        } else {
            updateButtons();
            updateTextArea();
        }

    }

    /**
     * Updates the up and down button. Disables it if there is no next/previous
     * instruction available.
     */
    private void updateButtons() {
        if (currentStep.getValue().equals(0)) {
            upButton.setDisable(true);
        } else {
            upButton.setDisable(false);
        }

        if (currentStep.getValue().equals((preparationSteps.size() - 1))) {
            downButton.setDisable(true);
        } else {
            downButton.setDisable(false);
        }
    }

    /**
     * Updates the text area with the current instruction.
     */
    private void updateTextArea() {
        instructionTextArea.setText(preparationSteps.get(currentStep.getValue())
                .getInstruction());
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
        changeDetected.bind(instructionEditWidgetController.getChangeDetected());
    }

}



