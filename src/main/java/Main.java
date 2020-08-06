import backend.data.DataHandler;
import backend.dataclasses.ShoppingList;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        DataHandler.initialize();
        System.out.print(ShoppingList.getInstance().toString());

        //RecipeReader.readRecipes();
    }
}
