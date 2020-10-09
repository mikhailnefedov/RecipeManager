package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
public final class GroceryItem implements Comparable<GroceryItem> {

    /**
     * Id of this grocery item in database.
     */
    private int id;
    /**
     * name of this grocery item.
     */
    private StringProperty name;
    /**
     * Affiliated grocery category, Stringproperty for frontend callbacks.
     */
    private StringProperty affiliatedGroceryCategory;
    /**
     * Object of the affiliated grocery category.
     */
    private GroceryCategory affiliatedObjectGroceryCategory;

    public GroceryItem() {
    }

    public GroceryItem(GroceryCategory affiliatedCategory, String name) {
        this.name = new SimpleStringProperty("");
        this.name.set(name);

        this.affiliatedGroceryCategory = new SimpleStringProperty("");
        this.affiliatedGroceryCategory.set(affiliatedCategory.toString());
        this.affiliatedObjectGroceryCategory = affiliatedCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getID() {
        return this.id;
    }

    /**
     * Setter for hibernate
     */
    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name.get();
    }

    /**
     * Setter for hibernate
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty();
        this.name.set(name);
    }

    /**
     * Get the grocery category this item belongs to.
     *
     * @return grocery category
     */
    @ManyToOne()
    @JoinColumn(name = "groceryCategoryID")
    public GroceryCategory getGroceryCategory() {
        return affiliatedObjectGroceryCategory;
    }

    /**
     * Setter for hibernate.
     *
     * @param category
     */
    public void setGroceryCategory(GroceryCategory category) {
        this.affiliatedObjectGroceryCategory = category;

        this.affiliatedGroceryCategory = new SimpleStringProperty("");
        this.affiliatedGroceryCategory.set(category.toString());
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

    public void updateName(String name) {
        this.name.set(name);
    }

    public void updateAffiliatedGroceryCategory(GroceryCategory category) {
        this.affiliatedObjectGroceryCategory = category;
        this.affiliatedGroceryCategory.set(category.toString());
    }
}
