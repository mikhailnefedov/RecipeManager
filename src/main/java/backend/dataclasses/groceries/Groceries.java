package backend.dataclasses.groceries;

import backend.dataclasses.recipe.Recipe;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the list of grocery items of the program.
 */
public class Groceries {

    private static Groceries instance = null;
    private ShoppingList shoppingList;
    /**
     * Observable Map for frontend view.
     */
    private ObservableList<GroceryItem> observableItems;
    private ObservableList<GroceryCategory> observableCategories;

    private Groceries() {
        shoppingList = new ShoppingList();
        observableItems = FXCollections.observableArrayList(
                item -> new Observable[]{item.nameProperty(),
                        item.affiliatedCategoryProperty()}
        );
        observableCategories = FXCollections.observableArrayList();
    }

    public static Groceries getInstance() {

        if (instance == null) {
            instance = new Groceries();
        }
        return instance;
    }

    /**
     * Initializes ShoppingList by loading data into an observable list and a
     * HashMap that contains every Category and its items.
     *
     * @param categoriesAndItems map of categories and their items
     */
    public void initialize(HashMap<GroceryCategory, ArrayList<GroceryItem>>
                                   categoriesAndItems) {
        for (GroceryCategory category : categoriesAndItems.keySet()) {
            observableCategories.add(category);
            observableItems.addAll(categoriesAndItems.get(category));
        }
    }

    /**
     * Returns list of observable items for javafx frontend.
     *
     * @return observable list of grocery items
     */
    public ObservableList<GroceryItem> getObservableItems() {
        return observableItems;
    }

    /**
     * Gets the current grocery categories.
     *
     * @return Set of grocery categories
     */
    public Set<GroceryCategory> getGroceryCategories() {
        HashSet<GroceryCategory> set = new HashSet();
        for (GroceryCategory category : observableCategories) {
            set.add(category);
        }
        return set;
    }

    /**
     * Adds a new grocery item to the observable list.
     *
     * @param newItem new grocery item
     */
    public void addGroceryItem(GroceryItem newItem) {
        observableItems.add(newItem);
    }

    /**
     * Checks if an item is already saved.
     *
     * @param itemName the item to be checked. Important is not the object, but
     *                 its name and category
     * @param category category of the item
     * @return true, if it already exists | else, if not
     */
    public boolean isItemInList(String itemName, GroceryCategory category) {
        return observableItems.stream()
                .filter(item -> item.getName().equals(itemName))
                .anyMatch(item -> item.getGroceryCategory().equals(category));
    }

    /**
     * Removes the grocery item from the observable list and the hashmap.
     *
     * @param item grocery item that shall be deleted
     */
    public void deleteGroceryItem(GroceryItem item) {
        observableItems.remove(item);
    }

    /**
     * Creates a shopping list for a given list of recipes.
     * @param recipes the recipes themselves
     */
    public void createShoppingList(ArrayList<Recipe> recipes) {
        shoppingList.createShoppingList(recipes);
    }

}
