package frontend;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class WindowController {

    @FXML
    private TableView<Recipe> recipeTable;
    @FXML
    private TableColumn<Recipe, String> idColumn;
    @FXML
    private TableColumn<Recipe, String> titleColumn;
    @FXML
    private TableColumn<Recipe, String> categoryColumn;
    @FXML
    private TableColumn<Recipe, Integer> timeColumn;
    @FXML
    private TableColumn<Recipe, URL> sourceColumn;

    private ObservableList<Recipe> getRecipes() {
        return Recipes.getInstance().getSavedRecipes();
    }

    private void loadDataIntoTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("recipeLink"));

        recipeTable.setItems(getRecipes());
    }

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

    public void handleCategoriesClick() throws IOException {
        Scene scene = new Scene(loadFXML("RecipeCategoriesWindow"));
        Stage stage = new Stage();
        stage.setTitle("RecipeManager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    protected void initialize() {

        loadDataIntoTable();
    }
}
