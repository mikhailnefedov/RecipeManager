import dataclasses.ShoppingList;
import data.*;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        DataHandler dataHandler = new DataHandler();
        dataHandler.initialize();
        System.out.print(ShoppingList.getInstance().toString());

        //RecipeReader.readRecipes();
    }
}
