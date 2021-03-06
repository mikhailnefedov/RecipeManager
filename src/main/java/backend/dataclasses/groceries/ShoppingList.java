package backend.dataclasses.groceries;

import backend.dataclasses.recipe.Recipe;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipe.uses.Quantity;
import backend.report.SimpleShoppingListPdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Creates a shopping list for the recipes.
 */
public class ShoppingList {

    private HashMap<GroceryCategory, HashMap<GroceryItem, ArrayList<Quantity>>> shoppingList;

    public ShoppingList() {
        shoppingList = new HashMap<>();
    }

    public HashMap<GroceryCategory, HashMap<GroceryItem, ArrayList<Quantity>>> getShoppingList() {
        return shoppingList;
    }

    public void createShoppingList(ArrayList<Recipe> recipeList) {
        for (Recipe recipe : recipeList) {
            addIngredientsToShoppingList(recipe.getIngredients());
        }

        System.out.println(shoppingList.toString());
        generateShoppingListPdf();
    }

    private void addIngredientsToShoppingList(List<Ingredient> ingredientList) {
        for (Ingredient ingredient : ingredientList) {
            shoppingList.putIfAbsent(
                    ingredient.getItem().getGroceryCategory(),
                    new HashMap<>());

            shoppingList.get(ingredient.getItem().getGroceryCategory())
                    .putIfAbsent(
                            ingredient.getItem(),
                            new ArrayList<>());

            handleMergeOfQuantityList(ingredient);
        }
    }

    /**
     * Merges the quantity of the ingredient into the shopping hashmap.
     *
     * @param ingredient the quantity from this ingredient will be added to the
     *                   hashmap
     */
    private void handleMergeOfQuantityList(Ingredient ingredient) {
        Quantity newQuantity = new Quantity(ingredient.getQuantity().getAmount(),
                ingredient.getQuantity().getMeasurementUnit());

        HashMap<GroceryItem, ArrayList<Quantity>> itemHashMap =
                shoppingList.get(ingredient.getItem().getGroceryCategory());
        ArrayList<Quantity> quantities = itemHashMap.get(ingredient.getItem());

        Quantity quantityFromList =
                getQuantity(quantities, newQuantity.getMeasurementUnit());
        if (quantityFromList != null) {
            quantityFromList.mergeQuantity(newQuantity);
        } else {
            quantities.add(newQuantity);
        }
    }

    /**
     * Gets the quantity from the quantities list that has the same
     * MeasurementUnit.
     *
     * @param quantities list of quantities
     * @param unit       MeasurementUnit that is used for filtering the list
     * @return quantity, if found | null, else
     */
    private Quantity getQuantity(ArrayList<Quantity> quantities,
                                 Quantity.MeasurementUnit unit) {
        Optional<Quantity> quantity = quantities.stream()
                .filter(q -> q.getMeasurementUnit().equals(unit))
                .findFirst();
        return quantity.orElse(null);
    }

    private void generateShoppingListPdf() {
        SimpleShoppingListPdf pdf = new SimpleShoppingListPdf();
        try {
            pdf.generate(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
