package backend.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reader for categories.xml.
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
     * @return HashMap with (Strings) grocery categories and their items.
     */
    public static HashMap<String, ArrayList<String>> readCategories() {

        HashMap<String, ArrayList<String>> categoriesAndItems = new HashMap<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GroceryCategory;");

            while (rs.next()) {
                int categoryID = rs.getInt("id");
                String categoryName = rs.getString("name");
                categoriesAndItems.put(categoryName, getItems(categoryID));
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
     * @param categoryID id of the category
     * @return List of items of the category
     */
    private static ArrayList<String> getItems(int categoryID) {

        ArrayList<String> items = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name "
                    + "FROM GroceryItem "
                    + "where grocerycategorykey=" + categoryID + ";");

            while (rs.next()) {
                items.add(rs.getString("name"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
