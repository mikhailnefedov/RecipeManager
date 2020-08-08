package frontend;

import backend.data.DataHandler;
import backend.dataclasses.ShoppingList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException{
        scene = new Scene(loadFXML("Window"));
        stage.setTitle("RecipeManager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        try {
            DataHandler.initialize();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.print(ShoppingList.getInstance().toString());


        launch();



    }
}
