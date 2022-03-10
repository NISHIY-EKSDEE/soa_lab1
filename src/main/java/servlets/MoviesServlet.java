package servlets;

import dao.MovieDao;
import model.Movie;
import service.MovieService;
import validators.ValidateFieldsException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/movies/*")
public class MoviesServlet extends HttpServlet {

    public static final String SORTING_PARAM = "sortBy";
    public static final String SELECTED_PAGE_PARAM = "selectedPage";
    public static final String LIMIT_PARAM = "limit";

    public static final String NAME_PARAM = "name";
    public static final String LOC_X_LEFT_PARAM = "x1";
    public static final String LOC_X_RIGHT_PARAM = "x2";
    public static final String LOC_Y_LEFT_PARAM = "y1";
    public static final String LOC_Y_RIGHT_PARAM = "y2";
    public static final String OSCARS_COUNT_LEFT_PARAM = "oscarsCount1";
    public static final String OSCARS_COUNT_RIGHT_PARAM = "oscarsCount2";
    public static final String GOLDEN_PALM_COUNT_LEFT_PARAM = "goldenPalmCount1";
    public static final String GOLDEN_PALM_COUNT_RIGHT_PARAM = "goldenPalmCount2";
    public static final String BUDGET_LEFT_PARAM = "budget1";
    public static final String BUDGET_RIGHT_PARAM = "budget2";
    public static final String GENRE_PARAM = "genre";
    public static final String CREATION_DATE_PARAM = "creationDate";

    public static final String DIRECTOR_NAME_PARAM = "directorName";
    public static final String DIRECTOR_BIRTHDAY_PARAM = "birthdayDate";

    private MovieService movieService;

    public MoviesServlet() {
        this.movieService = new MovieService();
    }

    private MovieRequestParams getFilterParams(HttpServletRequest request) throws ValidateFieldsException {
        return new MovieRequestParams(
                request.getParameter(NAME_PARAM),
                request.getParameter(LOC_X_LEFT_PARAM),
                request.getParameter(LOC_X_RIGHT_PARAM),
                request.getParameter(LOC_Y_LEFT_PARAM),
                request.getParameter(LOC_Y_RIGHT_PARAM),
                request.getParameter(OSCARS_COUNT_LEFT_PARAM),
                request.getParameter(OSCARS_COUNT_RIGHT_PARAM),
                request.getParameter(GOLDEN_PALM_COUNT_LEFT_PARAM),
                request.getParameter(GOLDEN_PALM_COUNT_RIGHT_PARAM),
                request.getParameter(BUDGET_LEFT_PARAM),
                request.getParameter(BUDGET_RIGHT_PARAM),
                request.getParameter(GENRE_PARAM),
                request.getParameter(CREATION_DATE_PARAM),
                request.getParameter(DIRECTOR_NAME_PARAM),
                request.getParameter(DIRECTOR_BIRTHDAY_PARAM),
                request.getParameter(SORTING_PARAM),
                request.getParameter(SELECTED_PAGE_PARAM),
                request.getParameter(LIMIT_PARAM)
        );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String pathInfo = request.getPathInfo();
        System.out.print(pathInfo);
        try {
            if (pathInfo == null) {
                MovieRequestParams filterParams = getFilterParams(request);
                movieService.getAllMovies(request, response, filterParams);
            } else {
                String[] parts = pathInfo.split("/");
                if (parts.length > 1) {
                    switch (parts[1]) {
                        // http://localhost:45857/lab1/movies/count_oscars_count_less/value
                        case "count_oscars_count_less":
                            try {
                                Integer value = Integer.valueOf(parts[2]);
                                movieService.countOscarsCountLess(value, request, response);
                            } catch (NumberFormatException e) {
                                request.setAttribute("msg", "Value must be an integer");
                                response.setStatus(400);
                            }
                            break;
                        // http://localhost:45857/lab1/movies/get_by_name_start/value
                        case "get_by_name_start":
                            movieService.getByNameStart(parts[2], request, response);
                            break;
                        default:
                            try {
                                Integer id = Integer.valueOf(parts[1]);
                                movieService.getMovieById(request, response, id);
                            } catch (NumberFormatException e) {
                                request.setAttribute("msg", "Id must be an integer");
                                response.setStatus(400);
                            }
                            break;
                    }
                }
            }
        } catch (ValidateFieldsException ex) {
            movieService.sendErrorList(request, response, ex);
        } catch (Exception ex) {
            response.setStatus(400);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            movieService.createMovie(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] servletPath = request.getPathInfo().split("/");
        try {
            Integer id = Integer.valueOf(servletPath[1]);
            movieService.updateMovie(request, response, id);
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Id must be an integer");
        } catch (EntityNotFoundException e) {
            request.setAttribute("msg", e.getLocalizedMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String[] servletPath = request.getPathInfo().split("/");
        try {
            Integer id = Integer.valueOf(servletPath[1]);

            movieService.deleteMovie(request, response, id);
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Id must be an integer");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            request.setAttribute("msg", e.getLocalizedMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
