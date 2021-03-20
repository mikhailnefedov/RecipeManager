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
    private MeasurementUnit unit;


    public Quantity(String quantity) {
        String[] quantityArray = quantity.split(" ");
        String amount = quantityArray[0];
        String unit = quantityArray[1];

        this.amount = Double.parseDouble(amount);
        this.unit = MeasurementUnit.valueOf(unit);
    }

    public Quantity(String amount, String unit) {
        this.amount = Double.parseDouble(amount);
        this.unit = MeasurementUnit.valueOf(unit);
    }

    public Quantity(String amount, MeasurementUnit unit) {
        this.amount = Double.parseDouble(amount);
        this.unit = unit;
    }

    public Quantity(double amount, MeasurementUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Returns all units of measurements for a grocery item.
     *
     * @return Set of MeasurementUnits
     */
    public static Set<MeasurementUnit> getAllMeasurementUnits() {
        return Set.of(MeasurementUnit.values());
    }

    public double getAmount() {
        return amount;
    }

    public MeasurementUnit getMeasurementUnit() {
        return unit;
    }

    @Override
    public String toString() {
        String builder = amount +
                " " +
                unit.toString();
        return builder;
        //e.g. "2.0 Kg"
    }

    public void mergeQuantity(Quantity anotherQuantity) {
        amount += anotherQuantity.getAmount();
    }

    /**
     * Different measurements. (Currently only in german)
     */
    public enum MeasurementUnit {
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
