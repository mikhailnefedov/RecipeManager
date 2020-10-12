package backend.dataclasses.recipe.uses;

import java.util.HashMap;
import java.util.Set;

public class Quantity {

    /**
     * How much/many ingredient/s are needed for the recipe.
     */
    private double amount;
    /**
     * Unit of the ingredient.
     */
    private MeasurementUnits unit;


    public Quantity(String quantity) {
        String[] quantityArray = quantity.split(" ");
        String amount = quantityArray[0];
        String unit = quantityArray[1];

        this.amount = Double.parseDouble(amount);
        this.unit = MeasurementUnits.valueOf(unit);
    }

    public Quantity(String amount, String unit) {
        this.amount = Double.parseDouble(amount);
        this.unit = MeasurementUnits.valueOf(unit);
    }

    /**
     * Returns all units of measurements for a grocery item.
     *
     * @return Set of MeasurementUnits
     */
    public Set<MeasurementUnits> getAllUnitMeasurements() {
        return Set.of(MeasurementUnits.values());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(amount);
        builder.append(" ");
        builder.append(unit.toString());
        return builder.toString();
    }

    /**
     * Different measurements. (Currently only in german)
     */
    private enum MeasurementUnits {
        Bd, //Bund
        Bl, //Blatt
        EL, //Esslöffel
        Kg, //Kilogramm
        Kn, //Knolle
        L, //Liter
        Pkg, //Packung
        St, //Stück
        TL, //Teelöffel
        Wf, //Würfel
        Z //Zehe
    }
}
