package backend.dataclasses;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShoppingList {

    private static ShoppingList instance = null;
    private HashMap<GroceryCategory, HashMap<GroceryItem, Quantity>> categoriesAndItems;

    private ShoppingList() {

        categoriesAndItems = new HashMap<>();
    }

    public static ShoppingList getInstance() {

        if (instance == null) {
            instance = new ShoppingList();
        }
        return instance;
    }

    public void addCategoryWithItems(String catStr, ArrayList<String> items) {

        GroceryCategory category = new GroceryCategory(catStr);
        HashMap<GroceryItem, Quantity> itemList = createItemList(items);
        categoriesAndItems.put(category, itemList);
    }

    private  HashMap<GroceryItem, Quantity> createItemList(ArrayList<String> items) {

        HashMap<GroceryItem, Quantity> itemMap = new HashMap<>();
        for (String itemName : items) {
            itemMap.put(new GroceryItem(itemName), null);
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

}
