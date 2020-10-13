package backend.dataclasses.recipecategories;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Convert(converter = backend.converter.StringPropertyConverter.class)
    private StringProperty abbreviation;
    @Convert(converter = backend.converter.StringPropertyConverter.class)
    private StringProperty name;

    public RecipeCategory() {
        //no-arg constructor for ORMLite
    }

    public RecipeCategory(String abbreviation, String name) {
        this.abbreviation = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.abbreviation.set(abbreviation);
        this.name.set(name);
    }

    public int getID() {
        return id;
    }

    public String getAbbreviation() {
        return abbreviation.get();
    }

    /**
     * Use for changing/updating id of this object
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation.set(abbreviation);
    }

    public String getName() {
        return name.get();
    }

    /**
     * Use for changing/updating name of this object
     */
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty abbreviationProperty() {
        return abbreviation;
    }

}
