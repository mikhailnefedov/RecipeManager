package backend.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Changes/Adds/Deletes records of RecipeCategory table from database.
 */
public final class RecipeCategoryWriter {

    /**
     * Connection to RecipeManagerDB.
     */
    private static Connection connection;

    private RecipeCategoryWriter() {
    }

    /**
     * Sets the connection to the database.
     *
     * @param c Connection to database (should be to RecipeManagerDB)
     */
    public static void setConnectionToDatabase(Connection c) {
        connection = c;
    }

    /**
     * Changes saved information about a category in xml.
     *
     * @param oldID           saved id of category in xml
     * @param newID           new id of category
     * @param newCategoryName new name of category
     */
    public static void changeCategory(String oldID, String newID,
                                      String newCategoryName) {
        try {
            Statement stmt = connection.createStatement();
            String query = "UPDATE RecipeCategory "
                    + " SET id='" + newID + "',name='" + newCategoryName + "' "
                    + "WHERE id='" + oldID + "';";
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new record for the new recipe category.
     *
     * @param id           id of new category
     * @param categoryName name of new category
     */
    public static void writeNewCategory(String id, String categoryName) {

        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO RecipeCategory (id, name)"
                    + " VALUES ('" + id + "','" + categoryName + "');";
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes record of recipe category from database.
     *
     * @param id id of category
     */
    public static void removeCategory(String id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM RecipeCategory "
                    + " WHERE id='" + id + "';";
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}