package backend.dataclasses.recipecategories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    void categoryNameAlreadyExists() {
        Assertions.assertFalse(list.isCategoryNameNonExistent("ABC"));
        Assertions.assertTrue(list.isCategoryNameNonExistent("CBA"));
    }

    @Test
    void idAlreadyExists() {
        Assertions.assertFalse(list.isAbbreviationNonExistent("ABC"));
        Assertions.assertTrue(list.isAbbreviationNonExistent("C"));
    }

}
