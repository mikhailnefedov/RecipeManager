package dataclasses.Recipe;

import dataclasses.Category;

import java.net.URL;

/**
 * Models a recipe of the user.
 */
public class Recipe {

    private String id;
    private String title;
    private Category category;
    private URL recipeLink;
    //private Amount && Unit TODO: Implement ENUM For Unit
    private int time;
    private boolean vegetarian;
    //TODO: Implement Handling for ingridient list
    //TODO: Implement data structure for preparation
    private String comment;

    Recipe() { }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setRecipeLink(URL recipeLink) {
        this.recipeLink = recipeLink;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
