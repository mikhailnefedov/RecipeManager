package frontend;

import backend.data.DataHandler;
import backend.dataclasses.groceries.ShoppingList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    private static Scene scene;

    /**
     * Loads fxml from path with german Localisation
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

    public static void main(String[] args) {

        DataHandler.initialize();

        launch();


    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Window"));
        stage.setTitle("RecipeManager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
