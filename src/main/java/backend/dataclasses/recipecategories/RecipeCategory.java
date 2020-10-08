package backend.dataclasses.recipecategories;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RecipeCategory {

    private SimpleStringProperty id;
    private SimpleStringProperty name;

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

    public void setId(String id) {
        this.id = new SimpleStringProperty("");
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty("");
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty idProperty() {
        return id;
    }
}
