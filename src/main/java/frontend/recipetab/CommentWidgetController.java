package frontend.recipetab;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CommentWidgetController extends RecipeWidgetsController {

    @FXML
    private TextArea commentTextArea;

    /**
     * Sets the comment into the comment TextArea.
     *
     * @param comment comment of the recipe.
     */
    public void initializeComment(String comment) {
        commentTextArea.setText(comment);
    }

    /**
     * Allows editing of the comment TextArea.
     */
    public void enableEdit() {
        commentTextArea.setDisable(false);
    }

    /**
     * Sets the changeDetected property to true.
     */
    public void onChange() {
        changeDetected.setValue(true);
    }

    /**
     * Gets the comment text from this widget.
     *
     * @return comment
     */
    public String getComment() {
        return commentTextArea.getText();
    }
}
