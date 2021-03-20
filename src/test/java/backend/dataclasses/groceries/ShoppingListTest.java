package backend.dataclasses.groceries;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipe.uses.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingListTest {

    private static Recipe recipe1;
    private static Recipe recipe2;

    @BeforeAll
    public static void init() {
        GroceryCategory fruit = new GroceryCategory(1, "fruit");

        GroceryItem apple = new GroceryItem(fruit, "apple");
        apple.setId(0);
        GroceryItem banana = new GroceryItem(fruit, "banana");
        banana.setId(1);
        GroceryItem strawberry = new GroceryItem(fruit, "strawberry");
        strawberry.setId(2);

        recipe1 = new Recipe();
        Ingredient ingredient1Apple = new Ingredient(apple,
                new Quantity(2.0, Quantity.MeasurementUnit.Kg));
        Ingredient ingredient1Banana = new Ingredient(banana,
                new Quantity(1.0, Quantity.MeasurementUnit.EL));
        List<Ingredient> ingredients1List = new ArrayList<>(List.of(ingredient1Apple, ingredient1Banana));
        recipe1.setIngredients(ingredients1List);

        recipe2 = new Recipe();
        Ingredient ingredient2Apple = new Ingredient(apple,
                new Quantity(5.0, Quantity.MeasurementUnit.St));
        List<Ingredient> ingredients2List = new ArrayList<>(List.of(ingredient2Apple));
        recipe2.setIngredients(ingredients2List);
    }

    private ArrayList<Quantity> getQuantities(
            HashMap<GroceryCategory, HashMap<GroceryItem, ArrayList<Quantity>>> shopHashMap,
            Ingredient ingredientFromRecipe) {
        return shopHashMap.get(ingredientFromRecipe.getItem().getGroceryCategory())
                .get(ingredientFromRecipe.getItem());
    }

    @Test
    void createShoppingListForOneRecipe() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.createShoppingList(new ArrayList<>(List.of(recipe1)));
        HashMap<GroceryCategory, HashMap<GroceryItem, ArrayList<Quantity>>> shopHashMap =
                shoppingList.getShopList();

        for (Ingredient ingredient : recipe1.getIngredients()) {
            ArrayList<Quantity> quantities = getQuantities(shopHashMap, ingredient);
            Assertions.assertEquals(quantities.size(), 1);
            Quantity quantity = quantities.get(0);
            Assertions.assertEquals(quantity.getAmount(), ingredient.getQuantity().getAmount());
            Assertions.assertEquals(quantity.getMeasurementUnit(), ingredient.getQuantity().getMeasurementUnit());
        }
    }

    /**
     * Used to test the case : apple = [2.0Kg, 5.0 St]
     */
    @Test
    void createShoppingListWhereListAddHappens() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.createShoppingList(new ArrayList<>(List.of(recipe1,recipe2)));
        HashMap<GroceryCategory, HashMap<GroceryItem, ArrayList<Quantity>>> shopHashMap =
                shoppingList.getShopList();

        Ingredient appleIngredient = recipe2.getIngredients().get(0);
        ArrayList<Quantity> quantities = getQuantities(shopHashMap, appleIngredient);

        Assertions.assertEquals(quantities.size(), 2);
        for (Quantity quantity : quantities) {
            if (quantity.getMeasurementUnit().equals(Quantity.MeasurementUnit.Kg)) {
                Assertions.assertEquals(quantity.getAmount(),
                        recipe1.getIngredients().get(0).getQuantity().getAmount());
            } else {
                Assertions.assertEquals(quantity.getAmount(),
                        appleIngredient.getQuantity().getAmount());
            }
        }
    }

    /**
     * Tests the merging of 5.0 St + 5.0 St -> 10 St. by adding the same recipe
     * twice to the shopping list.
     */
    @Test
    void createShoppingListWhereMergingHappens() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.createShoppingList(new ArrayList<>(List.of(recipe2,recipe2)));
        HashMap<GroceryCategory, HashMap<GroceryItem, ArrayList<Quantity>>> shopHashMap =
                shoppingList.getShopList();

        double resultAmount = recipe2.getIngredients().get(0).getQuantity().getAmount();
        resultAmount *= 2;

        for (Ingredient ingredient : recipe2.getIngredients()) {
            ArrayList<Quantity> quantities = getQuantities(shopHashMap, ingredient);
            Assertions.assertEquals(quantities.size(), 1);
            Quantity quantity = quantities.get(0);
            Assertions.assertEquals(quantity.getAmount(), resultAmount);
            Assertions.assertEquals(quantity.getMeasurementUnit(), ingredient.getQuantity().getMeasurementUnit());
        }
    }
}
