package backend.dataclasses.recipe;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PreparationStep {

    @Id
    private int id;

    private int steporder;

    private String steptext;

    public PreparationStep() {

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
