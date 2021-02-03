package frontend.recipetab;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;

public abstract class RecipeWidgetsController {

    protected BooleanProperty changeDetected;

    @FXML
    public void initialize() {
        changeDetected = new SimpleBooleanProperty(false);
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
     * Sets the changeDetected property to false.
     */
    public void resetChangeDetected() {
        changeDetected.setValue(false);
    }
}
