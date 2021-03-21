package frontend.shoppinglist;

import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.Recipes;
import backend.report.SimpleShoppingListPdf;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class ShoppingListWidgetController {

    @FXML
    private TextField inputField;

    public void pdfCreation() {

        String input = inputField.getText();
        String[] ids = input.split(",");

        ArrayList<Recipe> recipes = new ArrayList<>();
        for (String idString : ids) {
            int id = Integer.parseInt(idString);
            Recipes.getInstance().getSavedRecipes().stream()
                    .filter(r -> r.getID() == id)
                    .findFirst()
                    .ifPresent(recipes::add);
        }

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.createShoppingList(recipes);
        try {
            new SimpleShoppingListPdf().generate(shoppingList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
