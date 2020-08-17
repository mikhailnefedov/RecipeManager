package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GroceryCategory implements Comparable<GroceryCategory> {

    private StringProperty name;

    public GroceryCategory(String name) {
        this.name = new SimpleStringProperty("");
        this.name.set(name);
    }

    @Override
    public String toString() {
        return name.get();
    }

    public int compareTo(GroceryCategory other) {

        String otherName = other.toString();
        return name.get().compareTo(otherName);
    }
}
