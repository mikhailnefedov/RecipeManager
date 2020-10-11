package backend.converter;

import backend.dataclasses.recipe.Portionsize;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PortionSizeConverter implements
        AttributeConverter<Portionsize, String> {

    @Override
    public String convertToDatabaseColumn(Portionsize portionsize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(portionsize.getAmount());
        stringBuilder.append(" ");
        stringBuilder.append(portionsize.getUnit());
        return stringBuilder.toString();
    }

    @Override
    public Portionsize convertToEntityAttribute(String s) {
        String[] portionArray = s.split(" "); //"1 Brot"
        String amount = portionArray[0];    //"1"
        String unit = portionArray[1];      //"Bread"
        return new Portionsize(Double.parseDouble(amount), unit);
    }
}
