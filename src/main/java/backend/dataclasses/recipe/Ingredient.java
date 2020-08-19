package backend.dataclasses.recipe;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;

public class Ingredient {

    private GroceryCategory category;
    private GroceryItem item;
    private Quantity quantity;

    public Ingredient(GroceryCategory category, GroceryItem item,
                      Quantity quantity) {
        this.category = category;
        this.item = item;
        this.quantity = quantity;
    }

    public String getCategoryString() {
        return category.toString();
    }

    public GroceryItem getItem() {
        return item;
    }

    public String getItemString() {
        return item.toString();
    }

    public String getQuantity() {
        return quantity.toString();
    }
}
