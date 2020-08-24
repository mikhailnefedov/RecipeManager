package backend.dataclasses.recipe;

import java.util.Set;

/**
 * Models the portionsize attribute of a recipe. E.g. 1 Cake.
 */
public class Portionsize {

    private double amount;
    private PortionUnit unit;

    /**
     * Constructor for Portionsize.
     *
     * @param amount amount of portions.
     * @param unit String of unit of this portion.
     */
    Portionsize(double amount, String unit) {
        this.amount = amount;
        this.unit = PortionUnit.valueOf(unit);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PortionUnit getUnit() {
        return unit;
    }

    public void setUnit(PortionUnit unit) {
        this.unit = unit;
    }

    /**
     * Gets the existing portion units as a Set.
     *
     * @return set of portion units
     */
    public Set<PortionUnit> getPortionUnits() {
        return Set.of(PortionUnit.values());
    }

    /**
     * Different portion units. Currently only in german.
     */
    private enum PortionUnit {
        Brot,           //bread
        Brötchen,       //bun
        Kuchen,         //cake
        Portionen,      //portions
        Stücke,         //pieces
        Torte,          //cake
    }
}
