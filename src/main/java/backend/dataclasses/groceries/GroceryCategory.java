package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GroceryCategory implements Comparable<GroceryCategory> {

    private int id;
    private StringProperty name;

    public GroceryCategory() {
    }

    public GroceryCategory(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty("");
        this.name.set(name);
    }

    @Id
    @GeneratedValue
    public int getID() {
        return this.id;
    }

    /**
     * Setter for hibernate.
     */
    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
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
