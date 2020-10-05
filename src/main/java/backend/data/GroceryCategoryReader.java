package backend.data;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.recipecategories.RecipeCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Gets database records from GroceryCategory and GroceryItem tables.
 */
public final class GroceryCategoryReader {

    private GroceryCategoryReader() { }

    /**
     * Connection to RecipeManagerDB.
     */
    private static Connection connection;

    /**
     * Sets the connection to the database.
     *
     * @param c Connection to database (should be to RecipeManagerDB)
     */
    public static void setConnectionToDatabase(Connection c) {
        connection = c;
    }

    /**
     * Gets the grocery categories and the grocery items as a HashMap from the
     * database.
     *
     * @return HashMap with grocery categories and their items.
     */
    public static HashMap<GroceryCategory, ArrayList<GroceryItem>>
    readCategories() {

        HashMap<GroceryCategory, ArrayList<GroceryItem>> categoriesAndItems =
                new HashMap<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GroceryCategory;");

            while (rs.next()) {
                int categoryID = rs.getInt("id");
                String categoryName = rs.getString("name");

                GroceryCategory category = new
                        GroceryCategory(categoryID, categoryName);
                categoriesAndItems.put(category, getItems(category));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoriesAndItems;
    }

    /**
     * Gets the items from a specific category.
     *
     * @param category category to which the items belong
     * @return List of items of the category
     */
    private static ArrayList<GroceryItem> getItems(GroceryCategory category) {

        ArrayList<GroceryItem> items = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *"
                    + "FROM GroceryItem "
                    + "where grocerycategoryID=" + category.getID() + ";");

            while (rs.next()) {
                int itemID = rs.getInt("id");
                String itemName = rs.getString("name");

                items.add(new GroceryItem(itemID, itemName, category));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Returns number of recipes that use the grocery item from database.
     *
     * @param item grocery item used/not used by recipes
     * @return number of recipes
     */
    public static int getNumberOfRecipesToCategory(GroceryItem item) {

        int sum = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(r.id) AS total "
                    + "FROM Recipe r, uses u, GroceryItem g "
                    + "WHERE r.id = u.recipeID "
                    + "AND u.groceryitemID = g.id "
                    + "AND g.id = " + item.getID() + ";");

            while (rs.next()) {
                sum = rs.getInt("total");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
}
