package backend.dataclasses.recipe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PreparationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "steporder")
    private int stepOrder;
    @Convert(converter = backend.converter.StringPropertyConverter.class)
    private StringProperty instruction;
    @ManyToOne()
    @JoinColumn(name = "recipeID")
    private Recipe recipe;

    public PreparationStep() {
        this.instruction = new SimpleStringProperty("");
    }

    public int getId() {
        return id;
    }

    public String getInstruction() {
        return instruction.get();
    }

    public void setInstruction(String instruction) {
        this.instruction.set(instruction);
    }

    public StringProperty getInstructionProperty() {
        return instruction;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
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
