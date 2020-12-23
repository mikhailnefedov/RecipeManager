package frontend.RecipeTab;

import backend.dataclasses.recipe.PreparationStep;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.List;

public class PreparationStepWidgetController {
    @FXML
    private TextArea instructionTextArea;
    @FXML
    private Button upButton;
    @FXML
    private Button editButton;
    @FXML
    private Button downButton;

    private int currentStep;
    private List<PreparationStep> preparationSteps;

    /**
     * Initializes the controller for a new recipe. Initializes the GUI of the
     * widget.
     *
     * @param preparationSteps lsit of preparation steps of the recipe.
     */
    public void initialize(List<PreparationStep> preparationSteps) {
        currentStep = 0;
        this.preparationSteps = preparationSteps;

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
        if (currentStep == 0) {
            upButton.setDisable(true);
        } else {
            upButton.setDisable(false);
        }

        if (currentStep == preparationSteps.size() - 1) {
            downButton.setDisable(true);
        } else {
            downButton.setDisable(false);
        }
    }

    /**
     * Updates the text area with the current instruction.
     */
    private void updateTextArea() {
        instructionTextArea.setText(preparationSteps.get(currentStep)
                .getInstruction());
    }

    /**
     * Shows the next instruction in the widget.
     */
    public void nextInstruction() {
        currentStep++;
        updateButtons();
        updateTextArea();
    }

    /**
     * Shows the previous instruction in the widget.
     */
    public void previousInstruction() {
        currentStep--;
        updateButtons();
        updateTextArea();
    }


}



