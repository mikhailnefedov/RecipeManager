package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GroceryItem implements Comparable<GroceryItem>{

    private StringProperty name;
    private StringProperty affiliatedGroceryCategory;

    public GroceryItem(String name, GroceryCategory affiliatedCategory) {
        this.name = new SimpleStringProperty("");
        this.affiliatedGroceryCategory = new SimpleStringProperty("");
        this.name.set(name);
        this.affiliatedGroceryCategory.set(affiliatedCategory.toString());
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
}
