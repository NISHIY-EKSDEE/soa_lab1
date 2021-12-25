package servlets;

import dao.MovieDao;
import model.Movie;
import model.typesForXml.PaginationResult;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@WebServlet("/pages/*")
public class JspServlet extends HttpServlet {

    private final MovieDao movieDao;

    public JspServlet() {
        this.movieDao = new MovieDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/main":
                    PaginationResult movies = movieDao.getAllMovies();
                    request.setAttribute("pagesQuality", IntStream.range(1, movies.getTotalPages() + 1).toArray());
                    request.setAttribute("totalItems", movies.getTotalItems());
                    request.setAttribute("movies", movies.getList());
                    request.getRequestDispatcher("/jsp/main-page.jsp").forward(request, response);
                    break;
                case "/add-movie-form":
                    showNewForm(request, response);
                    break;
                case "/edit-form":
                    showEditForm(request, response);
                    break;
                case "/get-by-id-form":
                    showGetByIdForm(request, response);
                    break;
                case "/movie-names-groups":
                    showMovieNamesGroups(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    response.sendRedirect("/lab1/movies");
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/movie-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Optional<Movie> existingMovie = movieDao.getMovieById(id);
            request.setAttribute("movie", existingMovie.get());
            request.getRequestDispatcher("/jsp/movie-form.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/jsp/404.jsp").forward(request, response);
        }
    }

    private void showGetByIdForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Optional<Movie> movie = movieDao.getMovieById(id);
            movie.ifPresent(value -> request.setAttribute("movie", value));
        } catch (NumberFormatException e) {
//            request.setAttribute("msg", "Id must be an integer");
//            response.setStatus(400);
        } catch (EntityNotFoundException e) {
            request.setAttribute("msg", e.getLocalizedMessage());
            response.setStatus(404);
        }
        request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
    }

    private void showMovieNamesGroups(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Object []> groups = movieDao.getNameGroups();
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("/jsp/movie-names-groups.jsp").forward(request, response);
    }
}
