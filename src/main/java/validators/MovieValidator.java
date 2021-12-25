package validators;


import model.Error;
import model.typesForXml.JaxbMovie;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MovieValidator {

    private final PersonValidator personValidator;
    private final CoordinatesValidator coordinatesValidator;

    public MovieValidator() {
        personValidator = new PersonValidator();
        coordinatesValidator = new CoordinatesValidator();
    }

    public List<Error> validate(JaxbMovie movie) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        for (Field f : JaxbMovie.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(movie) == null && !f.getName().equals("id")) {
                errorList.add(new Error(f.getName(), (String.format("Movie %s is not specified correctly", f.getName()))));
            }
        }
        if (movie.getName() != null && movie.getName().isEmpty()) {
            errorList.add(new Error("name", "Movie name must not be empty"));
        }
        if (movie.getOscarsCount() != null && movie.getOscarsCount() <= 0) {
            errorList.add(new Error("oscarsCount","Movie oscars count must be positive"));
        }
        if (movie.getGoldenPalmCount() != null && movie.getGoldenPalmCount() <= 0) {
            errorList.add(new Error("goldenPalmCount", "Movie golden palm count must be positive"));
        }
        if (movie.getBudget() != null && movie.getBudget() <= 0) {
            errorList.add(new Error("budget","Movie budget must be positive"));
        }
        errorList.addAll(personValidator.validate(movie.getDirector()));
        errorList.addAll(coordinatesValidator.validate(movie.getCoordinates()));
        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        return errorList;
    }
}
