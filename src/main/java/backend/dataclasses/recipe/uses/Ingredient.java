package backend.dataclasses.recipe.uses;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.recipe.Recipe;

import javax.persistence.*;

@Entity
@Table(name = "uses")
public class Ingredient {


    @Id
    @GeneratedValue
    @Column(name = "usesID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "recipeID")
    private Recipe recipe;
    @ManyToOne
    @JoinColumn(name = "groceryitemID")
    private GroceryItem item;
    @Transient
    private GroceryCategory category;
    @Convert(converter = backend.converter.QuantityConverter.class)
    private Quantity quantity;

    public Ingredient() {

    }

    public Ingredient(GroceryCategory category, GroceryItem item,
                      Quantity quantity) {
        this.category = category;
        this.item = item;
        this.quantity = quantity;
    }

    public String getCategoryString() {
        return item.getGroceryCategory().toString();
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
