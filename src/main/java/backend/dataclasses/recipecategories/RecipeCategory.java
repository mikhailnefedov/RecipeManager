package backend.dataclasses.recipecategories;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecipeCategory {

    private StringProperty id;
    private StringProperty name;

    public RecipeCategory(String id, String name) {
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.id.set(id);
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty idProperty() {
        return id;
    }
}
