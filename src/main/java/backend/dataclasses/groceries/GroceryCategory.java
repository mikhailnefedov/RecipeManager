package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GroceryCategory implements Comparable<GroceryCategory> {

    private int id;
    private StringProperty name;

    public GroceryCategory(int id, String name) {
        this.id = id;
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

    public int getID() {
        return id;
    }
}
