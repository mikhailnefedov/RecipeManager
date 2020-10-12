package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class GroceryCategory implements Comparable<GroceryCategory> {

    @Id
    @GeneratedValue
    private int id;
    @Convert(converter = backend.converter.StringPropertyConverter.class)
    private StringProperty name;

    public GroceryCategory() {
    }

    public GroceryCategory(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty("");
        this.name.set(name);
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GroceryCategory other = (GroceryCategory) obj;
        return Objects.equals(id, other.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
