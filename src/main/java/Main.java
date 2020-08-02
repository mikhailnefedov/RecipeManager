import dataclasses.ListOfPurchasableItems;
import datahandler.*;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        CategoryReader.readCategories();
        System.out.print(ListOfPurchasableItems.getInstance().toString());
    }
}
