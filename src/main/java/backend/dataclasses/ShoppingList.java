package backend.dataclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShoppingList {

    private static ShoppingList instance = null;
    private HashMap<Category, HashMap<Item, Quantity>> categoriesAndItems;

    private ShoppingList() {

        categoriesAndItems = new HashMap<Category, HashMap<Item, Quantity>>();
    }

    public static ShoppingList getInstance() {

        if (instance == null) {
            instance = new ShoppingList();
        }
        return instance;
    }

    public void addCategoryWithItems(String catStr, ArrayList<String> items) {

        Category category = new Category(catStr);
        HashMap<Item, Quantity> itemList = createItemList(items);
        categoriesAndItems.put(category, itemList);
    }

    private  HashMap<Item, Quantity> createItemList(ArrayList<String> items) {

        HashMap<Item, Quantity> itemMap = new HashMap<Item, Quantity>();
        for (String itemName : items) {
            itemMap.put(new Item(itemName), null);
        }
        return itemMap;
    }

    @Override
    public String toString() {

        ArrayList<Category> categories = new ArrayList<Category>(categoriesAndItems.keySet());
        Collections.sort(categories);

        StringBuilder stringText = new StringBuilder();
        stringText.append("All Categories").append("\n");
        stringText.append("---------------").append("\n");
        for (Category category : categories) {
            stringText.append(category.toString()).append(":\n");

            ArrayList<Item> items = new ArrayList<Item>
                    (categoriesAndItems.get(category).keySet());
            Collections.sort(items);
            for (Item item : items) {
                stringText.append(item.toString()).append("\n");
            }
            stringText.append("---" + "\n");
        }

        return stringText.toString();
    }

}
