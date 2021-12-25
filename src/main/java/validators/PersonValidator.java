package validators;
import model.Error;
import model.typesForXml.JaxbPerson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PersonValidator {
    private final LocationValidator locationValidator;

    public PersonValidator() {
        this.locationValidator = new LocationValidator();
    }

    public List<Error> validate(JaxbPerson person) throws IllegalAccessException {
        List<Error> errorList = new ArrayList<>();
        if (person == null) {
            return errorList;
        }
        for (Field f : JaxbPerson.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(person) == null) {
                errorList.add(new Error(f.getName(), String.format("Person %s is not specified", f.getName())));
            }
        }
        errorList.addAll(locationValidator.validate(person.getLocation()));

        return errorList;
    }
}
