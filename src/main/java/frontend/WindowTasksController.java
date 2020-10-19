package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class WindowTasksController {

    /**
     * Loads fxml from path with german Localisation.
     *
     * @param fxml name of fxml resource
     * @return loaded object hierarchy from fxml document.
     * @throws IOException If resource not found
     */
    private static Parent loadFXML(String fxml) throws IOException {
        Locale locale = new Locale("de", "DE");
        String resourcePath = "RecipeManager";
        ResourceBundle bundle = ResourceBundle.getBundle(resourcePath, locale);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
    }

    /**
     * Opens a new window corresponding to the name of the fxml resource.
     *
     * @param nameOfFXMLFile name of fxml (without .fxml) that will be loaded
     * @param titleOfWindow  window title
     * @throws IOException failed or interrupted I/O operations
     */
    public void openNewWindow(String nameOfFXMLFile, String titleOfWindow)
            throws IOException {
        Scene scene = new Scene(loadFXML(nameOfFXMLFile));
        Stage stage = new Stage();
        stage.setTitle(titleOfWindow);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Handler for a button click on categories. Opens new window for the
     * recipe categories.
     *
     * @throws IOException failed or interrupted I/O operations
     */
    public void handleCategoriesClick() throws IOException {
        openNewWindow("RecipeCategoriesWindow",
                "Rezeptkategorien bearbeiten");
    }

    /**
     * Handler for a button click on grocery items. Opens new window for the
     * grocery items.
     *
     * @throws IOException failed or interrupted I/O operations
     */
    public void handleGroceryItemsClick() throws IOException {
        openNewWindow("GroceryItemsWindow",
                "Zutaten bearbeiten");
    }
}
