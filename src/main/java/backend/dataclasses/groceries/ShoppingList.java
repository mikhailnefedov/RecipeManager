package backend.dataclasses.groceries;

import backend.dataclasses.recipe.Quantity;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ShoppingList {

    private static ShoppingList instance = null;
    /**
     * Map that serves as a template for every recipe. It contains every
     * Category and its Items. Serves also as a container for saving the
     * Quantity amounts of the Recipe, so when recipes are combined into a
     * shopping list, the amounts can be merged together.
     */
    private HashMap<GroceryCategory, ArrayList<GroceryItem>> categoriesAndItems;
    /**
     * Observable Map for frontend view
     */
    private ObservableList<GroceryItem> observableItems;

    private ShoppingList() {

        categoriesAndItems = new HashMap<>();
        observableItems = FXCollections.observableArrayList(
                item -> new Observable[]{item.nameProperty(),
                        item.affiliatedCategoryProperty()}
        );
    }

    public static ShoppingList getInstance() {

        if (instance == null) {
            instance = new ShoppingList();
        }
        return instance;
    }

    //TODO: if something is added deleted --> add to Observable

    /**
     * Initializes ShoppingList by loading data into an observable list and a
     * HashMap that contains every Category and its items.
     *
     * @param categoriesAndItems map of categories and their items
     */
    public void initialize(HashMap<GroceryCategory, ArrayList<GroceryItem>>
                                   categoriesAndItems) {
        this.categoriesAndItems = categoriesAndItems;
        for (GroceryCategory category : categoriesAndItems.keySet()) {
            addItemListToObservable(categoriesAndItems.get(category));
        }
    }

    /**
     * Adds a list of items to the observable list for the frontend.
     *
     * @param items list of GroceryItem
     */
    private void addItemListToObservable(ArrayList<GroceryItem> items) {

        observableItems.addAll(items);
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
        return categoriesAndItems.keySet();
    }

    /**
     * Add new grocery item to the observable list and shopping list template.
     *
     * @param newItem new grocery item
     */
    public void addGroceryItem(GroceryItem newItem) {
        observableItems.add(newItem);
        categoriesAndItems.get(newItem.getGroceryCategory()).add(newItem);
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
        Stream<String> itemNames = categoriesAndItems.get(category)
                .stream().map(GroceryItem::toString);
        return itemNames.anyMatch(item -> item.equals(itemName));
    }

    /**
     * Gets the grocery category object corresponding to the parameter string.
     *
     * @param categoryName name of the category
     * @return grocery category
     */
    public GroceryCategory getGroceryCategory(String categoryName) {
        //TODO: Think its better to work with observator pattern, but for now this should work fine

        Supplier<Stream<GroceryCategory>> stream = () ->
                categoriesAndItems.keySet().stream()
                        .filter(category -> category.toString()
                                .equals(categoryName));
        if (stream.get().findFirst().isPresent()) {
            return stream.get().findFirst().get();
        } else {
            throw new IllegalArgumentException("Category does not exist");
        }

    }

    /**
     * Gets the corresponding grocery item to the parameter of a category.
     *
     * @param category category of the item
     * @param groceryItemName name of the item
     * @return grocery item corresponding to the data
     */
    public GroceryItem getGroceryItem(GroceryCategory category,
                                          String groceryItemName) {

        Supplier<Stream<GroceryItem>> items = () -> categoriesAndItems
                .get(category).stream()
                .filter(item -> item.toString().equals(groceryItemName));
        if (items.get().findFirst().isPresent()) {
            return items.get().findFirst().get();
        } else {
            throw new IllegalArgumentException("Item does not exist");
        }
    }

    /**
     * Removes the grocery item from the observable list and the hashmap.
     * @param item grocery item that shall be deleted
     */
    public void deleteGroceryItem(GroceryItem item) {
        observableItems.remove(item);
        categoriesAndItems.get(item.getGroceryCategory()).remove(item);
    }

}
