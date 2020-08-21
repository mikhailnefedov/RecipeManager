package backend.data;

import backend.dataclasses.groceries.GroceryCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Writer for categories.xml.
 */
public final class GroceryCategoryWriter {

    private GroceryCategoryWriter() { }

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
     * Inserts a new grocery item into the database and returns its id.
     *
     * @param category category of the item
     * @param itemName name of the item
     * @return id of the newly added item
     */
    public static int writeItem(GroceryCategory category, String itemName) {

        try {
            Statement stmt = connection.createStatement();

            int grocCatID = category.getID();
            String query = "INSERT INTO GroceryItem (name, grocerycategorykey)"
                    + " VALUES ('" + itemName + "','" + grocCatID + "');";

            stmt.execute(query);
            return stmt.executeQuery("SELECT last_insert_rowid()")
                    .getInt("last_insert_rowid()");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Something went wrong when " +
                    "trying to save a new grocery item into the database");
        }
    }

}
