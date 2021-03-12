package backend.dataclasses.recipe.uses;

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

    public Quantity(String amount, MeasurementUnits unit) {
        this.amount = Double.parseDouble(amount);
        this.unit = unit;
    }

    /**
     * Returns all units of measurements for a grocery item.
     *
     * @return Set of MeasurementUnits
     */
    public static Set<MeasurementUnits> getAllMeasurementUnits() {
        return Set.of(MeasurementUnits.values());
    }

    public double getAmount() {
        return amount;
    }

    public MeasurementUnits getMeasurementUnit() {
        return unit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(amount);
        builder.append(" ");
        builder.append(unit.toString());
        return builder.toString();
        //e.g. "2.0 Kg"
    }

    /**
     * Different measurements. (Currently only in german)
     */
    public enum MeasurementUnits {
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
