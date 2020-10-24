package frontend;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class Models controller of the main window.
 */
public class WindowController {

    @FXML
    private TableView<Recipe> recipeTable; //table view of recipes in frontend
    @FXML
    private TableColumn<Recipe, String> idColumn; //id column of recipeTable
    @FXML
    private TableColumn<Recipe, String> titleColumn; //title column " "
    @FXML
    private TableColumn<Recipe, String> categoryColumn; //category column " "
    @FXML
    private TableColumn<Recipe, Integer> timeColumn; //time column " "
    @FXML
    private TableColumn<Recipe, URL> sourceColumn; //source column " "
    @FXML
    private GridPane mainGridPane;
    @FXML
    private Tab recipeTab;
    /**
     * Controller of the recipe attributes in the tab.
     */
    private WindowRecipeTabController recipeTabController;

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
     * Initialization of window. Loads necessary data into the fxml components
     * of the view.
     */
    @FXML
    protected void initialize() {
        loadRecipesIntoTable();

        loadWindowTasks();
        loadRecipeTab();
    }

    /**
     * Loads the recipes into the table view.
     */
    private void loadRecipesIntoTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("RecipeCategory"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        recipeTable.setItems(getRecipes());
    }

    /**
     * Gets all saved recipes that are in the Singleton class of Recipes.
     *
     * @return list of all recipes
     */
    private ObservableList<Recipe> getRecipes() {
        return Recipes.getInstance().getSavedRecipes();
    }

    /**
     * Loads WindowTasks.fxml into the main gridpane.
     */
    private void loadWindowTasks() {
        try {
            mainGridPane.add(loadFXML("WindowTasks"), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads WindowRecipeTab.fxml into the tab.
     */
    private void loadRecipeTab() {
        try {
            Locale locale = new Locale("de", "DE");
            String resourcePath = "RecipeManager";
            ResourceBundle bundle = ResourceBundle.getBundle(resourcePath, locale);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("WindowRecipeTab.fxml"), bundle);
            recipeTab.setContent(fxmlLoader.load());
            recipeTabController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When a recipe on the table is clicked then the data of the recipe is
     * shown in the tab.
     */
    public void handleTableViewClickOnRecipe() {
        Recipe selectedRecipe = recipeTable.getSelectionModel()
                .getSelectedItem();
        recipeTabController.loadRecipeDataIntoTab(selectedRecipe);
    }

}
