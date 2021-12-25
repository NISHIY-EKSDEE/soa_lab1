package dao;

import model.Coordinates;
import model.Movie;
import model.Person;
import model.typesForXml.PaginationResult;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import servlets.MovieRequestParams;
import util.HibernateUtil;

import javax.persistence.criteria.*;
import java.util.*;

public class MovieDao {

    public MovieDao() {
    }

    private void applyPagination(Query<Movie> typedQuery, MovieRequestParams params) {
        int selectedPage = params.selectedPage;
        int numberOfRecordsPerPage = params.limit;
        int startIndex = (selectedPage * numberOfRecordsPerPage) - numberOfRecordsPerPage;
        typedQuery.setFirstResult(startIndex);
        typedQuery.setMaxResults(numberOfRecordsPerPage);
    }

    public PaginationResult getAllMovies() {
        Transaction transaction = null;
        PaginationResult res = new PaginationResult();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Movie> cr = cb.createQuery(Movie.class);
            Root<Movie> root = cr.from(Movie.class);

            CriteriaQuery<Movie> query = cr.select(root);
            Query<Movie> typedQuery = session.createQuery(query);

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            countQuery.select(cb.count(countQuery.from(Movie.class)));
            Long count = session.createQuery(countQuery).getSingleResult();

            List<Movie> list = typedQuery.getResultList();

            res = new PaginationResult((int) (count / 5) + 1, 1, count, list);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return res;
    }
    public PaginationResult getFilteredMovies(MovieRequestParams filterParams) {
        Transaction transaction = null;
        PaginationResult res = new PaginationResult();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Movie> cr = cb.createQuery(Movie.class);
            Root<Movie> root = cr.from(Movie.class);
            Join<Movie, Person> join = root.join("director");
            Join<Movie, Coordinates> joinLocation = root.join("coordinates");

            List<Predicate> predicates = filterParams.getPredicates(cb, root, join, joinLocation);

            if (filterParams.sorting != null) {
                if (filterParams.sorting.startsWith("coordinates")) {
                    cr.orderBy(cb.asc(joinLocation.get(filterParams.sorting.replaceAll("coordinates", "").toLowerCase())));
                } else if (filterParams.sorting.startsWith("director")) {
                    cr.orderBy(cb.asc(join.get(filterParams.sorting.replaceAll("director", "").toLowerCase())));
                } else {
                    cr.orderBy(cb.asc(root.get(filterParams.sorting)));
                }
            }

            CriteriaQuery<Movie> query = cr.select(root).where(predicates.toArray(new Predicate[0]));
            Query<Movie> typedQuery = session.createQuery(query);
            applyPagination(typedQuery, filterParams);

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            countQuery.select(cb.count(countQuery.from(Movie.class)));
            Long count = session.createQuery(countQuery).getSingleResult();

            List<Movie> list = typedQuery.getResultList();

            res = new PaginationResult((int) (count / filterParams.limit) + 1, filterParams.selectedPage, count, list);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return res;
    }

    public Optional<Movie> getMovieById(Integer id) {
        Transaction transaction = null;
        Movie movie = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            movie = session.get(Movie.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(movie);
    }

    public void addMovie(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean deleteMovie(Integer id) {
        Transaction transaction = null;
        boolean successful = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Movie movie = session.find(Movie.class, id);
            if (movie != null) {
                session.delete(movie);
                session.flush();
                successful = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return successful;
    }

    public void updateMovie(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(movie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Сгруппировать объекты по значению поля name, вернуть количество элементов в каждой группе.
    public List<Object []> getNameGroups() {
        Transaction transaction = null;
        List<Object []> result = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Object []> query = session.createQuery("select m.name, count(*) from Movie m group by m.name");
            result = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }
}
