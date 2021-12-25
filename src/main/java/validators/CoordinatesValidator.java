package validators;

import model.Error;
import model.typesForXml.JaxbCoordinates;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class CoordinatesValidator {
    public List<Error> validate(JaxbCoordinates coordinates) throws IllegalAccessException {
        List<Error> errorList = new ArrayList<>();
        if (coordinates == null) {
            return errorList;
        }
        for (Field f : JaxbCoordinates.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(coordinates) == null) {
                errorList.add(new Error(f.getName(), String.format("Coordinates %s is not specified", f.getName())));
            }
        }
        if (coordinates.getX() != null && coordinates.getX() > 523) {
            errorList.add(new Error("x", "Coordinate X max value is 523"));
        }
        return errorList;
    }
}
