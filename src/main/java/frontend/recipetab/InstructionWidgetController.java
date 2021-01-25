package frontend.recipetab;

import backend.dataclasses.recipe.PreparationStep;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Controller for InstructionWidget.fxml
 */
public class InstructionWidgetController {

    @FXML
    private VBox instructionVBox;
    @FXML
    private AnchorPane instructionAnchorPane;
    private List<PreparationStep> instructions;
    @FXML
    private BorderPane addButtonPane;

    /**
     * Loads the preparations steps into the frontend.
     *
     * @param instructions list of preparations steps of the recipe
     */
    public void initializeInstructions(List<PreparationStep> instructions) {

        instructionVBox.getChildren().remove(addButtonPane);
        this.instructions = instructions;
        for (PreparationStep step : instructions) {
            addNewTextArea(step);
        }
        instructionVBox.getChildren().add(addButtonPane);
    }

    /**
     * Adds a Textarea to the frontend showing the instruction of the
     * preparation step.
     *
     * @param step preparation step itself
     */
    private void addNewTextArea(PreparationStep step) {
        PreparationStepTextArea area = new PreparationStepTextArea(step);
        instructionVBox.getChildren().add(area);
    }

    /**
     * Adds an empty Textarea to the frontend. User can type in a new step.
     */
    public void addNewPreparationStep() {
        instructionVBox.getChildren().remove(addButtonPane);
        addNewTextArea(new PreparationStep());
        instructionVBox.getChildren().add(addButtonPane);
    }

    /**
     * Adds all new preparation steps to the instruction list of the recipe.
     * Method should be used when stage is closed by the user.
     */
    public void shutdown() {
        instructionVBox.getChildren().remove(addButtonPane);
        for (Node n : instructionVBox.getChildren()) {
            PreparationStepTextArea p = (PreparationStepTextArea) n;
            PreparationStep pStep = p.getPreparationStep();
            if (!instructions.contains(pStep)
                    && pStep.getInstruction() != null
                    && pStep.getInstruction().length() > 0) {
                instructions.add(pStep);
            }
        }
    }

    /**
     * Models Textarea holding a PreparationStep.
     */
    private class PreparationStepTextArea extends TextArea {

        PreparationStep preparationStep;

        /**
         * Sets the frontend specifications.
         * @param step preparation step of the textarea
         */
        PreparationStepTextArea(PreparationStep step) {
            preparationStep = step;

            int height = 100;
            this.setPrefHeight(height);
            this.setMaxHeight(height);
            this.setMinHeight(height);

            double width = instructionAnchorPane.widthProperty().doubleValue();
            this.setPrefWidth(width);
            this.setMaxWidth(width);
            this.setMinWidth(width);

            this.setText(step.getInstruction());
            this.setWrapText(true);

            this.setOnKeyTyped(inputMethodEvent ->
                    preparationStep.setInstruction(this.getText())
            );
        }

        /**
         * Returns the PreparationStep that the object holds.
         * @return PreparationStep itself
         */
        public PreparationStep getPreparationStep() {
            return preparationStep;
        }
    }
}


