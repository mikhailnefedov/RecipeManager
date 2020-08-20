package backend.data;

import java.sql.*;

public class TestDB {

    public static void main(String[] args) {
        Connection c;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/main/resources/RecipeManagerDB.db");

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM GroceryCategory;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
