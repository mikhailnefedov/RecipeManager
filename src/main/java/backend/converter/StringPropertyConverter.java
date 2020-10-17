package backend.converter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.AttributeConverter;

/**
 * Converter for converting StringProperty <--> String.
 */
public class StringPropertyConverter implements
        AttributeConverter<StringProperty, String> {

    @Override
    public String convertToDatabaseColumn(StringProperty stringProperty) {
        return stringProperty.get();
    }

    @Override
    public StringProperty convertToEntityAttribute(String string) {
        StringProperty stringProperty = new SimpleStringProperty("");
        stringProperty.set(string);
        return stringProperty;
    }
}
