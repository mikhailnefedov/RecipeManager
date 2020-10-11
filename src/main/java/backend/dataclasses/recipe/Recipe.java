package backend.dataclasses.recipe;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipecategories.ListOfRecipeCategories;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;

/**
 * Models a recipe of the user.
 */
@Entity
public class Recipe {

    private int id;
    private String title;
    private RecipeCategory category;
    private String source;
    private Portionsize portionsize;
    private int time;
    private boolean vegetarian;
    private ObservableList<Ingredient> ingredients;
    //TODO: Implement data structure for preparation
    private String comment;

    public Recipe() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets recipe category object!
     *
     * @return object of recipe category
     */
    @ManyToOne()
    @JoinColumn(name = "recipecategoryID")
    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    /**
     * Gets the name of the recipe category. (Needed for table views).
     *
     * @return name of recipe category
     */
    @Transient
    public String getRecipeCategory() {
        return category.getName();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Convert(converter = backend.converter.PortionSizeConverter.class)
    public Portionsize getPortionsize() {
        return portionsize;
    }

    public void setPortionsize(Portionsize portionsize) {
        this.portionsize = portionsize;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    @Transient
    public ObservableList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static class RecipeBuilder {

        private int id;
        private String title;
        private RecipeCategory category;
        private String recipeLink;
        private Portionsize portionsize;
        private int time;
        private boolean vegetarian;
        private ObservableList<Ingredient> ingredients;
        //TODO: Implement data structure for preparation
        private String comment;

        public RecipeBuilder() {
        }

        public RecipeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public RecipeBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RecipeBuilder category(String category) {
            this.category = ListOfRecipeCategories.getInstance().
                    getRecipeCategory(category);
            return this;
        }

        public RecipeBuilder recipeLink(String link) {
            this.recipeLink = link;
            return this;
        }

        public RecipeBuilder portionsize(double amount, String unit) {
            this.portionsize = new Portionsize(amount, unit);
            return this;
        }

        public RecipeBuilder time(int time) {
            this.time = time;
            return this;
        }

        /**
         * Sets the vegetarian value of the recipe builder.
         *
         * @param vegetarian 0 --> false , 1 --> true
         * @return this RecipeBuilder
         */
        public RecipeBuilder vegetarian(int vegetarian) {
            if (vegetarian == 0) {
                this.vegetarian = false;
            } else if (vegetarian == 1) {
                this.vegetarian = true;
            } else {
                throw new IllegalArgumentException("When trying to set the "
                        + "vegetarian value of a RecipeBuilder the Argument was"
                        + " not 0 or 1");
            }
            return this;
        }

        /**
         * Necessary method for initializing ingredients HashMap
         *
         * @return self
         */
        public RecipeBuilder ingredientsInitializer() {
            this.ingredients = FXCollections.observableArrayList();
            return this;
        }

        /**
         * Adds a new ingredient object corresponding to the parameters to the
         * ingredients list.
         *
         * @param categoryName name of the category
         * @param itemName     name of the item
         * @param amount       quantity of item
         * @return self
         */
        public RecipeBuilder addGroceryItem(String categoryName,
                                            String itemName, Quantity amount) {
            GroceryCategory category = ShoppingList.getInstance()
                    .getGroceryCategory(categoryName);
            GroceryItem item = ShoppingList.getInstance()
                    .getGroceryItem(category, itemName);

            this.ingredients.add(new Ingredient(category, item, amount));
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
            recipe.source = this.recipeLink;
            recipe.portionsize = this.portionsize;
            recipe.time = this.time;
            recipe.vegetarian = this.vegetarian;
            recipe.ingredients = FXCollections.observableArrayList();
            recipe.ingredients = this.ingredients;
            recipe.comment = this.comment;

            return recipe;
        }
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", source='" + source + '\'' +
                ", " + portionsize.toString() +
                ", time=" + time +
                ", vegetarian=" + vegetarian +
                ", ingredients=" + ingredients +
                ", comment='" + comment + '\'' +
                '}';
    }
}
