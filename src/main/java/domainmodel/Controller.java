package domainmodel;

import datasource.FileHandler;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.util.Comparator;


public class Controller {
    private MovieCollection movieCollection;

    private FileHandler fileHandler;
    private boolean changed = false;

    public Controller() {
        movieCollection = new MovieCollection();
        fileHandler = new FileHandler("movies.csv");
    }

    public void addMovie(String title, String director, int yearCreated, boolean inColor,
                         int lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        changed = true;
    }

    public ArrayList<Movie> getMovies() {
        return movieCollection.getMovies();
    }

    public ArrayList<Movie> getMoviesSortedByTitle() {
        return movieCollection.getMoviesSortedByTitle();
    }

    private Comparator<Movie> comparatorFor(SortField field) {
        return switch (field) {
            case TITLE -> new TitleComparator();
            case DIRECTOR -> new DirectorComparator();
            case YEAR -> new YearComparator();
            case LENGTH -> new LengthComparator();
            case GENRE -> new GenreComparator();
        };
    }
    public ArrayList<Movie> getMoviesSortedBy(SortField field) {
        return movieCollection.getMoviesSorted(comparatorFor(field));
    }
    public ArrayList<Movie> searchMovies(String searchText) {
        return movieCollection.searchMovies(searchText);
    }
    public boolean editMovie(Movie movie, String title, String director,
                             int yearCreated, boolean inColor,
                             int lengthInMinutes, String genre) {
        boolean edited = movieCollection.editMovie(movie, title, director,
                yearCreated, inColor, lengthInMinutes, genre);

        if (edited) {
            changed = true;
        }

        return edited;
    }


    public boolean deleteMovie(Movie movie) {
        boolean deleted = movieCollection.deleteMovie(movie);

        if (deleted) {
            changed = true;
        }

        return deleted;
    }

    public void markMovieAsWatched(Movie movie, LocalDate date) {
        movie.markAsWatched(date);
        changed = true;
    }

    public ArrayList<Movie> getMoviesNotWatchedForDays(int days) {
        LocalDate date = LocalDate.now().minusDays(days);
        return movieCollection.getMoviesNotWatchedSince(date);
    }

    public boolean saveMoviesIfChanged() throws FileNotFoundException {
        if (!changed) {
            return false;
        }

        fileHandler.saveMovies(movieCollection.getMovies());
        changed = false;
        return true;
    }

    public int loadMovies() throws FileNotFoundException {
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        for (Movie movie : loadedMovies) {
            movieCollection.addMovie(movie);
        }

        return loadedMovies.size();
    }

    public int getSkippedLineCount() {
        return fileHandler.getSkippedLineCount();
    }
}
