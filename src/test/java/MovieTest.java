
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class MovieTest {

    @Test
    void testConstructor() {
        // Arrange: Vi opretter en film
        Movie movie = new Movie(
                "Titanic",
                "James Cameron",
                1997,
                true,
                195,
                "Romance"
        );

        // Assert: Vi kontrollerer alle 6 oplysninger
        assertEquals("Titanic", movie.getTitle());
        assertEquals("James Cameron", movie.getDirector());
        assertEquals(1997, movie.getYearCreated());
        assertTrue(movie.isInColor());
        assertEquals(195, movie.getLengthInMinutes());
        assertEquals("Romance", movie.getGenre());
    }

    @Test
    void testSetTitle() {
        Movie movie = new Movie("Titanic", "James Cameron", 1997, true, 195, "Romance");

        movie.setTitle("Avatar");

        assertEquals("Avatar", movie.getTitle());
    }
    @Test
    void testNewMovieHasNeverBeenWatched() {
        Movie movie = new Movie(
                "Titanic", "James Cameron", 1997,
                true, 195, "Romance"
        );

        assertFalse(movie.hasBeenWatched());
        assertNull(movie.getLastWatched());
    }
    @Test
    void testMarkAsWatchedSavesDate() {
        Movie movie = new Movie(
                "Titanic", "James Cameron", 1997,
                true, 195, "Romance"
        );

        LocalDate date = LocalDate.of(2026, 10, 26);

        movie.markAsWatched(date);

        assertTrue(movie.hasBeenWatched());
        assertEquals(date, movie.getLastWatched());
    }
    @Test
    void testDaysSinceWatchedAcrossMonths() {
        Movie movie = new Movie(
                "Titanic", "James Cameron", 1997,
                true, 195, "Romance"
        );

        movie.markAsWatched(LocalDate.of(2025, 9, 30));

        long days = movie.getDaysSinceWatched(
                LocalDate.of(2025, 10, 26)
        );

        assertEquals(26, days);
    }
}
