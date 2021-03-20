package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * Models a grocery item that is needed for a recipe/ can be bought in a store.
 */
@Entity
public final class GroceryItem implements Comparable<GroceryItem> {

    /**
     * Id of this grocery item in database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Name of this grocery item.
     */
    @Convert(converter = backend.converter.StringPropertyConverter.class)
    private StringProperty name;
    /**
     * Affiliated grocery category that this item belongs to.
     */
    @ManyToOne()
    @JoinColumn(name = "groceryCategoryID")
    private GroceryCategory affiliatedGroceryCategory;

    public GroceryItem() {
    }

    public GroceryItem(GroceryCategory affiliatedCategory, String name) {
        this.name = new SimpleStringProperty("");
        this.name.set(name);
        this.affiliatedGroceryCategory = affiliatedCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }


    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Get the grocery category this item belongs to.
     *
     * @return grocery category
     */
    public GroceryCategory getGroceryCategory() {
        return affiliatedGroceryCategory;
    }

    public void setGroceryCategory(GroceryCategory category) {
        this.affiliatedGroceryCategory = category;
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

    /**
     * Gets the name property of the affiliated grocery category.
     *
     * @return name property of affiliated grocery category.
     */
    public StringProperty affiliatedCategoryProperty() {
        return affiliatedGroceryCategory.nameProperty();
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
        GroceryItem other = (GroceryItem) obj;
        return Objects.equals(id, other.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
