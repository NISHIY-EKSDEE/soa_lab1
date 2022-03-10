package servlets;

import model.*;
import model.Error;
import validators.ValidateFieldsException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MovieRequestParams {
    public final String name;
    public final Integer x1;
    public final Integer x2;
    public final Float y1;
    public final Float y2;
    public final Long oscarsCount1;
    public final Long oscarsCount2;
    public final Long goldenPalmCount1;
    public final Long goldenPalmCount2;
    public final Double budget1;
    public final Double budget2;
    public MovieGenre genre;
    public final String creationDate;
    public final String directorName;
    public final String directorBirthdayDate;
    public final String sorting;
    public final Integer selectedPage;
    public final Integer limit;

    private final List<Error> errorList;

    MovieRequestParams(
            String name,
            String x1,
            String x2,
            String y1,
            String y2,
            String oscarsCount1,
            String oscarsCount2,
            String goldenPalmCount1,
            String goldenPalmCount2,
            String budget1,
            String budget2,
            String genre,
            String creationDate,
            String directorName,
            String directorBirthdayDate,
            String sorting,
            String selectedPage,
            String limit
    ) throws ValidateFieldsException {
        this.errorList = new ArrayList<>();

        this.sorting = sorting;
        this.name = name;
        this.x1 = validateInteger(x1, "x1");
        this.x2 = validateInteger(x2, "x2");
        this.y1 = validateFloat(y1, "y1");
        this.y2 = validateFloat(y2, "y2");
        this.oscarsCount1 = validateLong(oscarsCount1, "oscarsCount1");
        this.oscarsCount2 = validateLong(oscarsCount2, "oscarsCount2");
        this.goldenPalmCount1 = validateLong(goldenPalmCount1, "goldenPalmCount1");
        this.goldenPalmCount2 = validateLong(goldenPalmCount2, "goldenPalmCount2");
        this.budget1 = validateDouble(budget1, "budget1");
        this.budget2 = validateDouble(budget2, "budget2");
        try {
            this.genre = genre == null ? null : MovieGenre.valueOf(genre);
        } catch (IllegalArgumentException ex) {
            this.genre = null;
            this.errorList.add(new Error("genre", (String.format("%s incorrect value", "genre"))));
        }
        this.creationDate = creationDate;
        this.directorName = directorName;
        this.directorBirthdayDate = directorBirthdayDate;
        this.selectedPage = validateInteger(selectedPage, 1);
        if (this.selectedPage <= 0) {
            this.errorList.add(new Error("selectedPage", "selectedPage incorrect value"));
        }
        this.limit = validateInteger(limit, 5);
        if (this.limit <= 0) {
            this.errorList.add(new Error("limit", "limit incorrect value"));
        }

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(this.errorList);
        }
    }

    private String like(String val) {
        return "%" + val + "%";
    }

    public List<javax.persistence.criteria.Predicate> getPredicates(
            CriteriaBuilder cb,
            Root<Movie> root,
            Join<Movie, Person> join,
            Join<Movie, Coordinates> joinCoordinates
    ) {
        List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(cb.like(root.get("name"), like(name)));
        }
        if (x1 != null) {
            predicates.add(cb.greaterThanOrEqualTo(joinCoordinates.get("x"), x1));
        }
        if (x2 != null) {
            predicates.add(cb.lessThanOrEqualTo(joinCoordinates.get("x"), x2));
        }
        if (y1 != null) {
            predicates.add(cb.greaterThanOrEqualTo(joinCoordinates.get("y"), y1));
        }
        if (y2 != null) {
            predicates.add(cb.greaterThanOrEqualTo(joinCoordinates.get("y"), y2));
        }
        if (oscarsCount1 != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("oscarsCount"), oscarsCount1));
        }
        if (oscarsCount2 != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("oscarsCount"), oscarsCount2));
        }
        if (goldenPalmCount1 != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("goldenPalmCount"), goldenPalmCount1));
        }
        if (goldenPalmCount2 != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("goldenPalmCount"), goldenPalmCount2));
        }
        if (budget1 != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("budget"), budget1));
        }
        if (budget2 != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("budget"), budget2));
        }
        if (genre != null) {
            predicates.add(cb.equal(root.get("genre"), genre));
        }
        if (creationDate != null) {
            predicates.add(cb.equal(root.get("creationDate"), creationDate));
        }
        if (directorName != null) {
            predicates.add(cb.like(join.get("name"), like(directorName)));
        }
        if (directorBirthdayDate != null) {
            predicates.add(cb.equal(join.get("birthday"), directorBirthdayDate));
        }
        return predicates;
    }

    private Integer validateInteger(String text, String fieldName) {
        if (text == null)
            return null;
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException ex) {
            this.errorList.add(new Error(fieldName, (String.format("%s incorrect format", fieldName))));
            return null;
        }
    }

    private Integer validateInteger(String text, int defaultValue) {
        if (text == null)
            return defaultValue;
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private Double validateDouble(String text, String fieldName) {
        if (text == null)
            return null;
        try {
            return Double.valueOf(text);
        } catch (NumberFormatException ex) {
            this.errorList.add(new Error(fieldName, (String.format("%s incorrect format", fieldName))));
            return null;
        }
    }

    private Long validateLong(String text, String fieldName) {
        if (text == null)
            return null;
        try {
            return Long.valueOf(text);
        } catch (NumberFormatException ex) {
            this.errorList.add(new Error(fieldName, (String.format("%s incorrect format", fieldName))));
            return null;
        }
    }

    private Float validateFloat(String text, String fieldName) {
        if (text == null)
            return null;
        try {
            return Float.valueOf(text);
        } catch (NumberFormatException ex) {
            this.errorList.add(new Error(fieldName, (String.format("%s incorrect format", fieldName))));
            return null;
        }
    }
}
