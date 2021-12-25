package model.typesForXml;

import model.Error;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Errors {
    @ElementList
    private List<Error> errors = null;

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
