package backend.data;

import backend.dataclasses.recipe.Quantity;
import backend.dataclasses.recipe.Recipe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Reader for Recipe table from database.
 */
public final class RecipeReader {

    /**
     * Connection to RecipeManagerDB.
     */
    private static Connection connection;

    private RecipeReader() {
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
     * Gets the saved recipes from the database.
     *
     * @return ArrayList with Recipes that are saved in the database
     */
    public static ArrayList<Recipe.RecipeBuilder> readRecipes() {

        ArrayList<Recipe.RecipeBuilder> recipes = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Recipe;");

            while (rs.next()) {

                Recipe.RecipeBuilder builder = new Recipe.RecipeBuilder();

                int recipeID = rs.getInt("id");
                builder.id(recipeID);

                String recipeCategory = rs.getString("recipecategoryID");
                builder.category(recipeCategory);

                String title = rs.getString("title");
                builder.title(title);

                String source = rs.getString("source");
                builder.recipeLink(source);

                String portionsize = rs.getString("portionsize");
                String[] portionArray = portionsize.split(" "); //"1 Brot"
                String amount = portionArray[0];    //"1"
                String unit = portionArray[1];      //"Bread"
                builder.portionsize(Double.parseDouble(amount), unit);

                int time = rs.getInt("time");
                builder.time(time);

                int vegetarian = rs.getInt("vegetarian");
                builder.vegetarian(vegetarian);

                String comment = rs.getString("comment");
                builder.comment(comment);

                handleIngredientList(recipeID, builder);

                recipes.add(builder);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    /**
     * Inserts every ingredient of the recipe into the Recipebuilder.
     *
     * @param recipeID id of the recipe
     * @param builder  Recipebuilder to which the ingredients will be added
     */
    private static void handleIngredientList(int recipeID,
                                             Recipe.RecipeBuilder builder) {
        builder.ingredientsInitializer();

        try {
            Statement stmt = connection.createStatement();
            String sqlQuery = "Select GroceryItem.name as itemName,"
                    + " GroceryCategory.name as catName,"
                    + " amount, unit "
                    + " From Recipe "
                    + " INNER JOIN uses on Recipe.id = uses.recipeID"
                    + " INNER JOIN GroceryItem on uses.groceryitemID = GroceryItem.id"
                    + " INNER JOIN GroceryCategory on GroceryItem.grocerycategoryID = GroceryCategory.id"
                    + " WHERE Recipe.id = " + recipeID + ";";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                String ingredientName = rs.getString("itemName");
                String grocCategoryName = rs.getString("catName");
                String amount = String.valueOf(rs.getFloat("amount"));
                String unit = rs.getString("unit");

                builder.addGroceryItem(grocCategoryName, ingredientName,
                        new Quantity(amount, unit));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
