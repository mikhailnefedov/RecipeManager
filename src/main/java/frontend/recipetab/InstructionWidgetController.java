package frontend.recipetab;

import backend.dataclasses.recipe.PreparationStep;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Controller for InstructionWidget.fxml
 */
public class InstructionWidgetController extends RecipeWidgetsController {

    @FXML
    private VBox instructionVBox;
    @FXML
    private AnchorPane instructionAnchorPane;
    private SimpleListProperty<PreparationStep> instructions;
    @FXML
    private BorderPane addButtonPane;
    private ArrayList<PreparationStep> stepsToAdd;

    /**
     * Loads the preparations steps into the frontend.
     *
     * @param instructions list of preparations steps of the recipe
     */
    public void initializeInstructions(SimpleListProperty<PreparationStep> instructions) {

        instructionVBox.getChildren().remove(addButtonPane);
        this.instructions = instructions;
        for (PreparationStep step : instructions) {
            addNewTextArea(step);
        }
        instructionVBox.getChildren().add(addButtonPane);

        stepsToAdd = new ArrayList<>();
    }

    /**
     * Appends a Textarea to the instructionVBox showing the instruction of the
     * preparation step.
     *
     * @param step preparation step itself
     */
    private void addNewTextArea(PreparationStep step) {
        PreparationStepTextArea area = new PreparationStepTextArea(step);
        instructionVBox.getChildren().add(area);
    }

    /**
     * Appends an empty Textarea to the instructionVBox for the user to type a
     * new preparation step.
     */
    public void addNewPreparationStep() {
        instructionVBox.getChildren().remove(addButtonPane);
        PreparationStep newPreparationStep = new PreparationStep();
        addNewTextArea(newPreparationStep);
        stepsToAdd.add(newPreparationStep);
        instructionVBox.getChildren().add(addButtonPane);
    }

    /**
     * Adds all new preparation steps to the instruction list of the recipe.
     * Method should be used when stage is closed by the user.
     */
    public void shutdown() {
        for (PreparationStep p : stepsToAdd) {
            if (p.getInstruction() != null && p.getInstruction().length() > 0) {
                instructions.add(p);
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
         *
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
                    preparationStep.setInstruction(this.getText()));
        }

    }
}