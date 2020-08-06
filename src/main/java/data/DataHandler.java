package data;

import dataclasses.ShoppingList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataHandler {

    public static void initialize() throws FileNotFoundException {

        CategoryReader catReader = new CategoryReader();
        HashMap<String, ArrayList<String>> catsAndItems =
                catReader.readCategories();

        ShoppingList shopList = ShoppingList.getInstance();
        for (String category : catsAndItems.keySet()) {
            shopList.addCategoryWithItems(category, catsAndItems.get(category));
        }

    }
}
