package frontend;

import backend.dataclasses.recipe.Portionsize;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Label recipeIDLabel; //ID label for shown recipe
    @FXML
    private TextField recipeNameTextField; //name textfield for shown recipe
    @FXML
    private ComboBox<String> recipeCategoryComboBox;
    @FXML
    private Button recipeSaveButton; //Button for saving changes/new recipe
    @FXML
    private Button recipeChangeButton; //Button for changing a recipe
    @FXML
    private Button recipeDeleteButton; //Button for deleting a recipe
    @FXML
    private TextField recipeTimeTextField; //Time textfield for shown recipe
    @FXML
    private CheckBox recipeVegetarianCheckbox; //Checkbox if recipe is vegetarian
    @FXML
    private TextField recipeSourceTextField; //textfield for shown recipe source
    @FXML
    private TextField recipePortionsizeAmountTextField;
    @FXML
    private ComboBox<Portionsize.PortionUnit> recipePortionsizeUnitComboBox;
    @FXML
    private TableView<Ingredient> recipeTabIngredientTable;
    @FXML
    private TableColumn<Ingredient, String> recipeTabIngredientCategoryColumn;
    @FXML
    private TableColumn<Ingredient, String> recipeTabIngredientItemColumn;
    @FXML
    private TableColumn<Ingredient, String> recipeTabIngredientQuantityColumn;
    @FXML
    private GridPane mainGridPane;

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
     * When a recipe on the table is clicked then the data of the recipe is
     * shown in the tab.
     */
    public void handleTableViewClickOnRecipe() {
        activateRecipeButtons();
        loadRecipeDataIntoTab();
    }

    /**
     * Activates the three buttons for a recipe: save, change, new.
     */
    private void activateRecipeButtons() {
        recipeSaveButton.setDisable(false);
        recipeChangeButton.setDisable(false);
        recipeDeleteButton.setDisable(false);
    }

    /**
     * Loads the data of the recipe into the tab.
     */
    private void loadRecipeDataIntoTab() {
        Recipe selectedRecipe = recipeTable.getSelectionModel().getSelectedItem();

        recipeIDLabel.setText(Integer.toString(selectedRecipe.getID()));
        recipeNameTextField.setText(selectedRecipe.getTitle());

        Set<String> categories = ListOfRecipeCategories.
                getInstance().getSavedRecipeCategories().stream().
                map(RecipeCategory::getName).collect(Collectors.toSet());
        recipeCategoryComboBox.getItems().addAll(categories);
        recipeCategoryComboBox.getSelectionModel().
                select(selectedRecipe.getRecipeCategory());


        recipeTimeTextField.setText(Integer.toString(selectedRecipe.getTime()));

        Portionsize portion = selectedRecipe.getPortionsize();
        recipePortionsizeAmountTextField.setText(Double.toString(portion.getAmount()));
        recipePortionsizeUnitComboBox.getItems().addAll(Portionsize.getPortionUnits());
        recipePortionsizeUnitComboBox.getSelectionModel().
                select(portion.getUnit());

        if (selectedRecipe.isVegetarian()) {
            recipeVegetarianCheckbox.setSelected(true);
        } else {
            recipeVegetarianCheckbox.setSelected(false);
        }
        recipeSourceTextField.setText(selectedRecipe.getSource());

        recipeTabIngredientCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryString"));
        recipeTabIngredientItemColumn.setCellValueFactory(new PropertyValueFactory<>("itemString"));
        recipeTabIngredientQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        recipeTabIngredientTable.setItems(selectedRecipe.getObservableIngredients());


    }


}
