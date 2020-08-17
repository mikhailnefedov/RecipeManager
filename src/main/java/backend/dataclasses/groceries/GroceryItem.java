package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GroceryItem implements Comparable<GroceryItem>{

    private StringProperty name;
    /**
     * Affiliated grocery category, Stringproperty for frontend callbacks.
     */
    private StringProperty affiliatedGroceryCategory;
    /**
     * Object of the affiliated grocery category.
     */
    private GroceryCategory affiliatedObjectGroceryCategory;

    public GroceryItem(String name, GroceryCategory affiliatedCategory) {
        this.name = new SimpleStringProperty("");
        this.affiliatedGroceryCategory = new SimpleStringProperty("");
        this.name.set(name);
        this.affiliatedGroceryCategory.set(affiliatedCategory.toString());
        this.affiliatedObjectGroceryCategory = affiliatedCategory;
    }

    @Override
    public String toString() {
        return name.get();
    }

    public int compareTo(GroceryItem other) {

        String otherName = other.toString();
        return name.get().compareTo(otherName);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty affiliatedCategoryProperty() {
        return affiliatedGroceryCategory;
    }

    /**
     * Get the grocery category this item belongs to.
     * @return grocery category
     */
    public GroceryCategory getGroceryCategory() {
        return affiliatedObjectGroceryCategory;
    }
}
