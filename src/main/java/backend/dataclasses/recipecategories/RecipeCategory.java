package backend.dataclasses.recipecategories;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Objects;

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

    public void setAbbreviation(String abbreviation) {
        this.abbreviation.set(abbreviation);
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

    public StringProperty abbreviationProperty() {
        return abbreviation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RecipeCategory other = (RecipeCategory) obj;
        return Objects.equals(id, other.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
