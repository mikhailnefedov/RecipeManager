package backend.dataclasses.recipecategories;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IDCreationTest {

    private static ListOfRecipeCategories list;

    @BeforeAll
    public static void init() {
        list = ListOfRecipeCategories.getInstance();
        ArrayList<RecipeCategory> someCategories = new ArrayList<>();
        someCategories.add(new RecipeCategory("B", "Bread"));
        someCategories.add(new RecipeCategory("A", "ABC"));
        someCategories.add(new RecipeCategory("AB", "ABCD"));
        someCategories.add(new RecipeCategory("ABC", "ABCDE"));
        list.addListOfRecipeCategories(someCategories);
    }

    @Test
    void CategoryNameAlreadyExists() {

        try {
            list.addRecipeCategory("ABC");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    void addCategories() {
        try {
            list.addRecipeCategory("ABCDEF");
            list.addRecipeCategory("Salate");
            list.addRecipeCategory("Saucen");
            list.addRecipeCategory("Suppen");
        } catch (IllegalArgumentException e) {
            fail();
        }

        ObservableList<RecipeCategory> ids = list.getSavedRecipeCategories();
        for (RecipeCategory category : ids) {
            switch (category.getName()) {
                case "ABCDEF":
                    assertEquals("ABCD", category.getId());
                    break;
                case "Salate":
                    assertEquals("S", category.getId());
                    break;
                case "Saucen":
                    assertEquals("Sa", category.getId());
                    break;
                case "Suppen":
                    assertEquals("Su", category.getId());
                    break;
            }
        }
    }
}
