package backend.dataclasses.recipe;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Models a recipe of the user.
 */
public class Recipe {

    private String id;
    private String title;
    private String category;
    private URL recipeLink;
    //private Amount && Unit TODO: Implement ENUM For Unit
    private int time;
    private boolean vegetarian;
    //TODO: Implement Handling for ingridient list
    //TODO: Implement data structure for preparation
    private String comment;

    public Recipe() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category; //TODO: create own category class
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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public URL getRecipeLink() {
        return recipeLink;
    }

    public int getTime() {
        return time;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public String getComment() {
        return comment;
    }

    public static class RecipeBuilder {

        private String id;
        private String title;
        private String category;
        private URL recipeLink;
        //private Amount && Unit TODO: Implement ENUM For Unit
        private int time;
        private boolean vegetarian;
        //TODO: Implement Handling for ingridient list
        //TODO: Implement data structure for preparation
        private String comment;

        public RecipeBuilder() {
        }

        public RecipeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RecipeBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RecipeBuilder category(String category) {
            this.category = category; //TODO: Implement category class for recipe categories
            return this;
        }

        public RecipeBuilder recipeLink(String link) {
            try {
                this.recipeLink = new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public RecipeBuilder time(String time) {
            this.time = Integer.parseInt(time);
            return this;
        }

        public RecipeBuilder vegetarian(String vegetarian) {
            this.vegetarian = Boolean.parseBoolean(vegetarian);
            return this;
        }

        public RecipeBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Recipe build() {
            Recipe recipe = new Recipe();
            recipe.id = this.id;
            recipe.title = this.title;
            recipe.category = this.category;
            recipe.recipeLink = this.recipeLink;
            recipe.time = this.time;
            recipe.vegetarian = this.vegetarian;
            recipe.comment = this.comment;

            return recipe;
        }
    }
}
