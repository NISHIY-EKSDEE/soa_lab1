package service;

import dao.MovieDao;
import model.Movie;
import model.typesForXml.Errors;
import model.typesForXml.JaxbMovie;
import model.typesForXml.PaginationResult;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import servlets.MovieRequestParams;
import util.Jaxb;
import util.ServerResponse;
import validators.MovieValidator;
import validators.QueryMapValidator;
import validators.ValidateFieldsException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

import static util.ServletUtil.getBody;

public class MovieService {

    private final MovieDao movieDao;
    private final Errors errorsList;
    private final MovieValidator movieValidator;

    public MovieService() {
        this.movieDao = new MovieDao();
        this.movieValidator = new MovieValidator();
        this.errorsList = new Errors();
    }

    public void createMovie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String movieBody = getBody(request);
            JaxbMovie movie = Jaxb.fromStr(movieBody, JaxbMovie.class);
            movieValidator.validate(movie);
            movieDao.addMovie(movie.toMovie());
            response.setStatus(201);
        } catch (ValidateFieldsException ex) {
            sendErrorList(request, response, ex);
        } catch (IllegalAccessException | JAXBException e) {
            response.setStatus(400);
        }
    }

    public void updateMovie(HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception {
        try {
            String movieBody = getBody(request);
            JaxbMovie movieData = Jaxb.fromStr(movieBody, JaxbMovie.class);
            movieData.setId(id);
            movieValidator.validate(movieData);
            Optional<Movie> movieFromDB = movieDao.getMovieById(movieData.getId());
            if (movieFromDB.isPresent()) {
                Movie updatingMovie = movieFromDB.get();
                updatingMovie.update(movieData);
                movieDao.updateMovie(updatingMovie);
                response.setStatus(204);
            } else {
                throw new EntityNotFoundException("No Movie with id=" + id);
            }
        } catch (ValidateFieldsException ex) {
            sendErrorList(request, response, ex);
        } catch (IllegalAccessException | JAXBException e) {
            response.setStatus(400);
        }
    }

    public void deleteMovie(HttpServletRequest request, HttpServletResponse response, Integer id) {
        if (movieDao.deleteMovie(id)) {
            response.setStatus(204);
        } else {
            throw new EntityNotFoundException("No Movie with id=" + id);
        }
    }

    public void getMovieById(HttpServletRequest request, HttpServletResponse response, Integer id) {
        Optional<Movie> movie = movieDao.getMovieById(id);
        if (movie.isPresent()) {
            sendMovie(request, response, movie.get());
        } else {
            response.setStatus(404);
        }
    }

    public void getAllMovies(HttpServletRequest request, HttpServletResponse response, MovieRequestParams filterParams) throws ServletException, IOException, Exception {
        try {
            PaginationResult movies = movieDao.getFilteredMovies(filterParams);
            sendMovies(request, response, movies);
        } catch (Exception e) {
            response.setStatus(400);
        }

    }

    public void countOscarsCountLess(int value, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long count = movieDao.countOscarsCountLess(value);
            if (count != null){
                sendMessage(request, response, count.toString());
            } else {
                response.setStatus(500);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getByNameStart(String value, HttpServletRequest request, HttpServletResponse response) {
        try {
            PaginationResult movies = movieDao.getByNameStart(value);
            sendMovies(request, response, movies);
        } catch (Exception e){
            response.setStatus(400);
        }
    }

    public void sendErrorList(HttpServletRequest request, HttpServletResponse response, ValidateFieldsException ex) {
        response.setStatus(400);
        errorsList.setErrors(ex.getErrorMsg());
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        try {
            serializer.write(errorsList, writer);
            String xml = writer.toString();
            response.getWriter().print(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(HttpServletRequest request, HttpServletResponse response, String message) {
        ServerResponse serverResponse = new ServerResponse(message);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        try {
            serializer.write(serverResponse, writer);
            String xml = writer.toString();
            response.getWriter().print(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMovie(HttpServletRequest request, HttpServletResponse response, Movie movie) {
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        try {
            serializer.write(movie, writer);
            String xml = writer.toString();
            response.getWriter().print(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMovies(HttpServletRequest request, HttpServletResponse response, PaginationResult movies) throws Exception {
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        serializer.write(movies, writer);
        String xml = writer.toString();
        response.getWriter().print(xml);
    }
}
