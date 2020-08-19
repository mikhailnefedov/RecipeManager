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
    private HashMap<GroceryCategory, HashMap<GroceryItem, Quantity>> categoriesAndItems;
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

    public void addCategoryWithItems(String catStr, ArrayList<String> items) {

        GroceryCategory category = new GroceryCategory(catStr);
        HashMap<GroceryItem, Quantity> itemList = createItemList(items, category);
        categoriesAndItems.put(category, itemList);
    }

    private HashMap<GroceryItem, Quantity> createItemList(
            ArrayList<String> items, GroceryCategory category) {

        HashMap<GroceryItem, Quantity> itemMap = new HashMap<>();
        for (String itemName : items) {
            GroceryItem groceryItem = new GroceryItem(itemName, category);
            itemMap.put(groceryItem, null);
            observableItems.add(groceryItem);
        }
        return itemMap;
    }

    @Override
    public String toString() {

        ArrayList<GroceryCategory> categories = new ArrayList<>(categoriesAndItems.keySet());
        Collections.sort(categories);

        StringBuilder stringText = new StringBuilder();
        stringText.append("All Categories").append("\n");
        stringText.append("---------------").append("\n");
        for (GroceryCategory category : categories) {
            stringText.append(category.toString()).append(":\n");

            ArrayList<GroceryItem> items = new ArrayList<>
                    (categoriesAndItems.get(category).keySet());
            Collections.sort(items);
            for (GroceryItem item : items) {
                stringText.append(item.toString()).append("\n");
            }
            stringText.append("---" + "\n");
        }

        return stringText.toString();
    }

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
        categoriesAndItems.get(newItem.getGroceryCategory()).put(newItem, null);
    }

    /**
     * Checks if an item is already saved.
     *
     * @param itemName the item to be checked. Important is not the object, but its
     *                 name and category
     * @param category category of the item
     * @return true, if it already exists | else, if not
     */
    public boolean isItemInList(String itemName, GroceryCategory category) {
        Set<GroceryItem> itemsFromCategory = categoriesAndItems
                .get(category).keySet();
        Stream<String> itemNames = itemsFromCategory.
                stream().map(GroceryItem::toString);
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
                .get(category).keySet().stream()
                .filter(item -> item.toString().equals(groceryItemName));
        if (items.get().findFirst().isPresent()) {
            return items.get().findFirst().get();
        } else {
            throw new IllegalArgumentException("Item does not exist");
        }
    }

}
