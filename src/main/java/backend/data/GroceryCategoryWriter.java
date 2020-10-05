package backend.data;

import backend.dataclasses.groceries.GroceryCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Adds/Changes records from GroceryCategory/GroceryItem tables from database.
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
            String query = "INSERT INTO GroceryItem (name, grocerycategoryID)"
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

    /**
     * Removes record of grocery item from database.
     *
     * @param id id of the grocery item
     */
    public static void removeGroceryItem(int id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM GroceryItem "
                    + " WHERE id='" + id + "';";
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the name and the affiliated grocery category of a grocery item
     * in the database.
     *
     * @param itemID id of the item that has changes to id
     * @param newName changed name of the item
     * @param categoryID id of the changed category, the item belongs to
     */
    public static void changeItem(int itemID, String newName, int categoryID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "UPDATE GroceryItem "
                    + "SET name='" + newName
                    + "',grocerycategoryID='" + categoryID + "' "
                    + "WHERE id='" + itemID + "';";
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
