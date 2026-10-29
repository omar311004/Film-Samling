import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class FileHandlerTest {
    @Test
    void testSaveAndLoadMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Titanic",
                "James Cameron",
                1997,
                true,
                195,
                "Romance"
        ));
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveMovies(movies);

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(1, loadedMovies.size());
        assertEquals("Titanic", loadedMovies.get(0).getTitle());
        assertEquals("James Cameron", loadedMovies.get(0).getDirector());
    }

    @Test
    void testLoadInvalidLine() {
        FileHandler fileHandler = new FileHandler();
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(1, fileHandler.getSkippedLineCount());
    }
    @Test
    void testDanishCharacters() {

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Bænken",
                "Per Fly",
                2000,
                true,
                93,
                "Drama"
        ));
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals("Bænken", loadedMovies.get(0).getTitle());
    }
    @Test
    void testDateIsPreserved() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Titanic",
                "James Cameron",
                1997,
                true,
                195,
                "Romance"
        ));
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(1997, loadedMovies.get(0).getYearCreated());
    }
}

