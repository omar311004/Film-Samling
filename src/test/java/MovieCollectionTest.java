import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class MovieCollectionTest {

    @Test
    void newMovieCollectionIsEmpty() {
        MovieCollection collection = new MovieCollection();

        assertEquals(0, collection.getMovies().size());
    }
    @Test
    void testMoviesNeverWatchedAreIncluded() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie(
                "Titanic", "James Cameron", 1997,
                true, 195, "Romance"
        );

        LocalDate date = LocalDate.of(2026, 10, 26);

        assertEquals(1, collection.getMoviesNotWatchedSince(date).size());
    }
    @Test
    void testMoviesWatchedBeforeCutoffAreIncluded() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie(
                "Titanic", "James Cameron", 1997,
                true, 195, "Romance"
        );

        collection.addMovie(
                "Avatar", "James Cameron", 2009,
                true, 162, "Sci-Fi"
        );

        collection.addMovie(
                "Frozen", "Chris Buck", 2013,
                true, 102, "Animation"
        );

        LocalDate cutoff = LocalDate.of(2026, 10, 20);

        collection.getMovies().get(0)
                .markAsWatched(LocalDate.of(2026, 10, 19));

        collection.getMovies().get(1)
                .markAsWatched(LocalDate.of(2026, 10, 20));

        collection.getMovies().get(2)
                .markAsWatched(LocalDate.of(2026, 10, 21));

        assertEquals(
                1,
                collection.getMoviesNotWatchedSince(cutoff).size()
        );
    }
}