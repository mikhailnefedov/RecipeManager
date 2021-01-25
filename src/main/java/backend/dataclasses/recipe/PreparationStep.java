package backend.dataclasses.recipe;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PreparationStep {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "steporder")
    private int stepOrder;
    private String instruction;

    public PreparationStep() {

    }

    public int getId() {
        return id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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
                ", stepOrder=" + stepOrder +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
