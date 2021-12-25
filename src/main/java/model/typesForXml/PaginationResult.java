package model.typesForXml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Movie;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResult {
    @Element
    private int totalPages;

    @Element
    private int currentPage;

    @Element
    private long totalItems;

    @ElementList
    private List<Movie> list;

    public PaginationResult() {
        totalPages = 0;
        currentPage = 0;
        totalItems = 0L;
        list = new ArrayList<>();
    }
}
