import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class FileHandlerTest {
    @Test
    void testSaveAndLoadMovies() throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Titanic",
                "James Cameron",
                1997,
                true,
                195,
                "Romance"
        ));
        FileHandler fileHandler = new FileHandler("test-movies.csv");
        fileHandler.saveMovies(movies);

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(1, loadedMovies.size());
        assertEquals("Titanic", loadedMovies.get(0).getTitle());
        assertEquals("James Cameron", loadedMovies.get(0).getDirector());
    }

    @Test
    void testLoadInvalidLine() throws Exception { java.nio.file.Files.writeString(
            java.nio.file.Path.of("test-invalid.csv"),
            "title;director;yearCreated;inColor;lengthInMinutes;genre;lastWatched\nUGYLDIG LINJE\n"
    );
        FileHandler fileHandler = new FileHandler("test-invalid.csv");
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(1, fileHandler.getSkippedLineCount());
    }
    @Test
    void testDanishCharacters() throws Exception {

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Bænken",
                "Per Fly",
                2000,
                true,
                93,
                "Drama"
        ));
        FileHandler fileHandler = new FileHandler("test-danish.csv");
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals("Bænken", loadedMovies.get(0).getTitle());
    }
    @Test
    void testDateIsPreserved() throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Titanic",
                "James Cameron",
                1997,
                true,
                195,
                "Romance"
        ));
        FileHandler fileHandler = new FileHandler("test-date.csv");
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(1997, loadedMovies.get(0).getYearCreated());
    }
}

