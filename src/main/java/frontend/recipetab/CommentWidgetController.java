package frontend.recipetab;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CommentWidgetController {

    @FXML
    private TextArea commentTextArea;
    private BooleanProperty changeDetected;

    @FXML
    public void initialize() {
        changeDetected = new SimpleBooleanProperty(false);
    }

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
     * Gets the changeDetected property.
     *
     * @return true, if user edit happened | false, else
     */
    public BooleanProperty getChangeDetected() {
        return changeDetected;
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
