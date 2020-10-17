package backend.converter;

import backend.dataclasses.recipe.uses.Quantity;

import javax.persistence.AttributeConverter;

/**
 * Converter for converting Quantity <--> String.
 */
public class QuantityConverter implements AttributeConverter<Quantity, String> {

    @Override
    public String convertToDatabaseColumn(Quantity quantity) {
        return quantity.toString();
    }

    @Override
    public Quantity convertToEntityAttribute(String s) {
        return new Quantity(s);
    }
}
