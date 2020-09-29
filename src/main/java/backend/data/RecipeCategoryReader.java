package backend.data;

import backend.dataclasses.recipecategories.RecipeCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Gets data from RecipeCategory table from database.
 */
public final class RecipeCategoryReader {

    /**
     * Connection to RecipeManagerDB.
     */
    private static Connection connection;

    private RecipeCategoryReader() {
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
     * Gets a list with the saved recipe categories from the database.
     *
     * @return list of saved recipe categories
     */
    public static ArrayList<RecipeCategory> readRecipeCategories() {

        ArrayList<RecipeCategory> recipeCategories = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RecipeCategory;");

            while (rs.next()) {
                String categoryID = rs.getString("id");
                String categoryName = rs.getString("name");

                RecipeCategory category =
                        new RecipeCategory(categoryID, categoryName);
                recipeCategories.add(category);

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeCategories;
    }

    /**
     * Returns number of recipes associated to the recipe category from database.
     *
     * @param category recipes are associated with this category
     * @return number of recipes
     */
    public static int getNumberOfRecipesToCategory(RecipeCategory category) {

        int sum = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(r.id) AS total "
                    + "FROM Recipe r, RecipeCategory c "
                    + "WHERE r.recipecategoryID = c.id and c.id = '"
                    + category.getId() + "';");

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
