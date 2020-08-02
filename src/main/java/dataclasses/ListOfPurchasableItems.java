package dataclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class ListOfPurchasableItems {

    private static ListOfPurchasableItems instance = null;
    private HashMap<Category, ArrayList<Item>> categoriesAndItems;

    private ListOfPurchasableItems() {

        categoriesAndItems = new HashMap<Category, ArrayList<Item>>();
    }

    public static ListOfPurchasableItems getInstance() {

        if (instance == null) {
            instance = new ListOfPurchasableItems();
        }
        return instance;
    }

    public void addCategoryWithItems(String catStr, ArrayList<String> items) {

        Category category = new Category(catStr);
        ArrayList<Item> itemList = createItemList(items);
        categoriesAndItems.put(category, itemList);
    }

    private ArrayList<Item> createItemList(ArrayList<String> items) {

        ArrayList<Item> itemList = new ArrayList<Item>();
        for (String itemName : items) {
            itemList.add(new Item(itemName));
        }
        return itemList;
    }

    @Override
    public String toString() {

        StringBuilder stringText = new StringBuilder();
        stringText.append("All Categories").append("\n");
        stringText.append("---------------").append("\n");
        for (Category category : categoriesAndItems.keySet()) {
            stringText.append(category.toString()).append(":\n");
            for (Item item : categoriesAndItems.get(category)) {
                stringText.append(item.toString()).append("\n");
            }
            stringText.append("---" + "\n");
        }

        return stringText.toString();
    }
}
