import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class FileHandlerTest {
    @AfterEach
    void cleanup() throws Exception {
        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("test-movies.csv"));
        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("test-invalid.csv"));
        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("test-danish.csv"));
        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("test-date.csv"));
        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("test-empty.csv"));
    }
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
        movies.add(new Movie(
                "Bænken",
                "Per Fly",
                2000,
                true,
                93,
                "Drama"
        ));
        movies.get(0).markAsWatched(java.time.LocalDate.of(2026, 10, 30));
        FileHandler fileHandler = new FileHandler("test-movies.csv");
        fileHandler.saveMovies(movies);

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(2, loadedMovies.size());
        assertEquals("Titanic", loadedMovies.get(0).getTitle());
        assertEquals("James Cameron", loadedMovies.get(0).getDirector());
        assertEquals("Bænken", loadedMovies.get(1).getTitle());
        assertEquals("Per Fly", loadedMovies.get(1).getDirector());
        assertEquals(2000, loadedMovies.get(1).getYearCreated());
        assertTrue(loadedMovies.get(1).isInColor());
        assertEquals(93, loadedMovies.get(1).getLengthInMinutes());
        assertEquals("Drama", loadedMovies.get(1).getGenre());
        assertFalse(loadedMovies.get(1).hasBeenWatched());
        assertTrue(loadedMovies.get(0).hasBeenWatched());
        assertEquals(java.time.LocalDate.of(2026, 10, 30),
                loadedMovies.get(0).getLastWatched());
        assertEquals(1997, loadedMovies.get(0).getYearCreated());
        assertTrue(loadedMovies.get(0).isInColor());
        assertEquals(195, loadedMovies.get(0).getLengthInMinutes());
        assertEquals("Romance", loadedMovies.get(0).getGenre());
    }

    @Test
    void testLoadInvalidLine() throws Exception { java.nio.file.Files.writeString(
            java.nio.file.Path.of("test-invalid.csv"),
            "title;director;yearCreated;inColor;lengthInMinutes;genre;lastWatched\n" +
                    "Titanic;James Cameron;1997;true;195;Romance;\n" +
                    "UGYLDIG LINJE\n" +
                    "ENDNU EN UGYLDIG LINJE\n"
    );
        FileHandler fileHandler = new FileHandler("test-invalid.csv");
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertEquals(2, fileHandler.getSkippedLineCount());
        assertEquals(1, loadedMovies.size());
        assertEquals("Titanic", loadedMovies.get(0).getTitle());
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
    @Test
    void testSaveAndLoadEmptyList() throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();

        FileHandler fileHandler = new FileHandler("test-empty.csv");
        fileHandler.saveMovies(movies);

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        assertTrue(loadedMovies.isEmpty());
    }
    @Test
    void testLoadNonexistentFile() {
        FileHandler fileHandler = new FileHandler("test-nonexistent.csv");

        assertThrows(java.io.FileNotFoundException.class,
                () -> fileHandler.loadMovies());
    }
}

