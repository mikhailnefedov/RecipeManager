package backend.dataclasses.recipe;

import backend.converter.PortionsizeConverter;
import backend.dataclasses.recipe.uses.Ingredient;
import backend.dataclasses.recipecategories.RecipeCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<PreparationStep> preparation;
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

    @Convert(converter = PortionsizeConverter.class)
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

    @OneToMany(mappedBy = "recipe")
    public Set<Ingredient> getIngredients() {
        return new HashSet<>(ingredients);
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = FXCollections.observableArrayList(ingredients);
    }

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "recipeID")
    public List<PreparationStep> getPreparation() {
        return preparation;
    }

    public void setPreparation(List<PreparationStep> preparation) {
        this.preparation = preparation;
    }

    @Transient
    public ObservableList<Ingredient> getObservableIngredients() {
        return ingredients;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", source='" + source + '\'' +
                ", portionsize=" + portionsize +
                ", time=" + time +
                ", vegetarian=" + vegetarian +
                ", ingredients=" + ingredients +
                ", preparation=" + preparation +
                ", comment='" + comment + '\'' +
                '}';
    }
}
