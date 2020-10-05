package frontend;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * Represents extended Tooltip class for Error label when user makes faulty
 * inputs.
 */
public class ErrorTooltip extends Tooltip {

    ErrorTooltip() {
        this.setShowDelay(Duration.millis(300));
    }

    public void clearText() {
        this.setText("");
    }

    public void addErrorMessage(String newError) {
        String existingErrorMessage = this.getText();
        String newErrorMessage;
        if (existingErrorMessage.length() > 0) {
            newErrorMessage = existingErrorMessage + "\n" + newError;
        } else {
            newErrorMessage = newError;
        }
        this.setText(newErrorMessage);
    }

}
