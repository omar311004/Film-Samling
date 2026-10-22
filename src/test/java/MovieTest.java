
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

}
