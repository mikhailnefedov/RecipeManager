package frontend.helper;

import frontend.Main;
import frontend.recipetab.InstructionWidgetController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class WindowLoader {

    /**
     * Creates the FXMLLoader for a specific fxml file. (Currently only german
     * localisation).
     *
     * @param fxml name of the fxml file
     * @return FXMLoader of the file.
     */
    private static FXMLLoader createFxmlLoader(String fxml) {
        Locale locale = new Locale("de", "DE");
        String resourcePath = "RecipeManager";
        ResourceBundle bundle = ResourceBundle.getBundle(resourcePath, locale);
        return new FXMLLoader(Main.class.getResource(fxml + ".fxml"), bundle);
    }

    /**
     * Loads the FXMLLoader of the fxml file.
     *
     * @param fxml name of fxml resource
     * @return loaded object hierarchy from fxml document.
     * @throws IOException If resource not found
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = createFxmlLoader(fxml);
        return fxmlLoader.load();
    }

    /**
     * Opens a new window corresponding to the name of the fxml resource.
     *
     * @param nameOfFXMLFile name of fxml (without .fxml) that will be loaded
     * @param titleOfWindow  window title
     * @throws IOException failed or interrupted I/O operations
     */
    public static void openNewWindow(String nameOfFXMLFile, String titleOfWindow)
            throws IOException {
        Scene scene = new Scene(loadFXML(nameOfFXMLFile));
        Stage stage = new Stage();
        stage.setTitle(titleOfWindow);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Opens a new window corresponding to the name of the fxml resource.
     *
     * @param nameOfFXMLFile name of fxml (without .fxml) that will be loaded
     * @param titleOfWindow  window title
     * @return controller of the opened window.
     * @throws IOException failed or interrupted I/O operations
     */
    public static Object openNewWindowReturnController(String nameOfFXMLFile,
                                                       String titleOfWindow) throws IOException {
        FXMLLoader fxmlLoader = createFxmlLoader(nameOfFXMLFile);
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(titleOfWindow);
        stage.setScene(scene);
        stage.setResizable(false);
        setShutdownMethods(stage, fxmlLoader.getController());
        stage.show();
        return fxmlLoader.getController();
    }

    /**
     * Sets setOnHidden method from the stage to the specific method of the
     * controller.
     *
     * @param stage the stage itself
     * @param controller controller of the stage
     */
    private static void setShutdownMethods(Stage stage, Object controller) {
        if (controller.getClass()
                .equals(frontend.recipetab.InstructionWidgetController.class)) {
            stage.setOnHidden(windowEvent ->
                    ((InstructionWidgetController) controller).shutdown());
        }
    }
}
