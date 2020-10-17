package backend.dataclasses.recipe.uses;

import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.recipe.Recipe;

import javax.persistence.*;
import java.util.Objects;

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
    @Convert(converter = backend.converter.QuantityConverter.class)
    private Quantity quantity;

    public Ingredient() {
    }

    public Ingredient(GroceryItem item,
                      Quantity quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ingredient other = (Ingredient) obj;
        return Objects.equals(id, other.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
