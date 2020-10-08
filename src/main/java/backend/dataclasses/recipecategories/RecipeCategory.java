package backend.dataclasses.recipecategories;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RecipeCategory {

    private StringProperty id;
    private StringProperty name;

    public RecipeCategory() {
        //no-arg constructor for ORMLite
    }

    public RecipeCategory(String id, String name) {
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.id.set(id);
        this.name.set(name);
    }

    @Id
    public String getId() {
        return id.get();
    }

    /**
     * Existence purely for hibernate. Do not use for changing attribute!
     */
    public void setId(String id) {
        this.id = new SimpleStringProperty("");
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    /**
     * Existence purely for hibernate. Do not use for changing attribute!
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty("");
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty idProperty() {
        return id;
    }

    /**
     * Use for changing/updating id of this object
     */
    public void updateID(String id) {
        this.id.set(id);
    }

    /**
     * Use for changing/updating name of this object
     */
    public void updateName(String name) {
        this.name.set(name);
    }

}
