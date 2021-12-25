package validators;

import model.Error;
import model.typesForXml.JaxbCoordinates;
import model.typesForXml.JaxbLocation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class LocationValidator {
    public List<Error> validate(JaxbLocation location) throws IllegalAccessException {
        List<Error> errorList = new ArrayList<>();
        if (location == null) {
            return errorList;
        }
        for (Field f : JaxbLocation.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(location) == null) {
                errorList.add(new Error(f.getName(), String.format("Location %s is not specified", f.getName())));
            }
        }
        return errorList;
    }
}
