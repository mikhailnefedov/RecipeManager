package backend.dataclasses.recipe;

import java.util.HashMap;
import java.util.Set;

public class Quantity {

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

    /**
     * Represents amount of the measurements of a grocery item that is needed
     * for a recipe.
     * For example: [Kg: 1,5 ; Pkg: 1]
     */
    private HashMap<MeasurementUnits, Double> amounts;

    public Quantity(String amount, String unit) {
        this.amounts = new HashMap<>();
        Double amountDouble = Double.parseDouble(amount);
        MeasurementUnits measurementUnit = MeasurementUnits.valueOf(unit);

        this.amounts.put(measurementUnit, amountDouble);
    }

    /**
     * Returns all units of measurements for a grocery item.
     * @return Set of MeasurementUnits
     */
    public Set<MeasurementUnits> getAllUnitMeasurements() {
        return Set.of(MeasurementUnits.values());
    }

    @Override
    public String toString() {
        if (amounts.size() == 0) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder();
            for (MeasurementUnits unit : amounts.keySet()) {
                builder.append("[").append(unit.toString()).append(": ");
                builder.append(amounts.get(unit)).append("]");
            }
            return builder.toString();
        }
    }
}
