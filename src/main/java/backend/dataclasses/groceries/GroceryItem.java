package backend.dataclasses.groceries;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public GroceryItem(int id, String name,
                       GroceryCategory affiliatedCategory) {
        this.id = id;
        this.name = new SimpleStringProperty("");
        this.name.set(name);

        this.affiliatedGroceryCategory = new SimpleStringProperty("");
        this.affiliatedGroceryCategory.set(affiliatedCategory.toString());
        this.affiliatedObjectGroceryCategory = affiliatedCategory;
    }

    @Override
    public String toString() {
        return name.get();
    }

    public int compareTo(GroceryItem other) {

        int categoryCompare = this.affiliatedObjectGroceryCategory
                .compareTo(other.getGroceryCategory());
        if (categoryCompare == 0) {
            String otherName = other.toString();
            return name.get().compareTo(otherName);
        } else {
            return categoryCompare;
        }

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

    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAffiliatedGroceryCategory(GroceryCategory category) {
        this.affiliatedObjectGroceryCategory = category;
        this.affiliatedGroceryCategory.set(category.toString());
    }
}
