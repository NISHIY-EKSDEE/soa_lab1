package validators;

import model.Error;
import model.typesForXml.JaxbCoordinates;
import util.DateBuilder;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class QueryMapValidator {
    private final List<String> integerKeys = Arrays.asList(
            "x1", "x2", "y1", "y2", "oscarsCount1", "oscarsCount2", "goldenPalmCount1", "goldenPalmCount2", "budget1", "budget2");

    public List<Error> validate(Map<String, String[]> queryMap) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        if (queryMap == null) {
            return errorList;
        }
        checkIntegers(queryMap, integerKeys, errorList);

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        return errorList;
    }

    private void checkIntegers(Map<String, String[]> queryMap, List<String> keys, List<Error> errorList) {
        for (String key : keys) {
            if (queryMap.containsKey(key)) {
                try {
                    Integer.parseInt(queryMap.get(key)[0]);
                } catch (NumberFormatException ex) {
                    errorList.add(new Error(key, String.format("Filter param %s must be an integer", key)));
                }
            }
        }
    }
}
