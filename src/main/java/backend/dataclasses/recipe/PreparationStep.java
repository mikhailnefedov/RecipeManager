package backend.dataclasses.recipe;

import backend.dataclasses.recipecategories.RecipeCategory;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PreparationStep {

    @Id
    private int id;

    private int steporder;

    private String steptext;

    public PreparationStep() {

    }

    public int getId() {
        return id;
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
        PreparationStep other = (PreparationStep) obj;
        return Objects.equals(id, other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PreparationStep{" +
                "id=" + id +
                ", steporder=" + steporder +
                ", steptext='" + steptext + '\'' +
                '}';
    }
}
