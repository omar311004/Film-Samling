
package domainmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorTest {

    private Movie casablanca;
    private Movie psycho;
    private Movie jaws;
    private Controller controller;

    @BeforeEach
    void setUp() {
        casablanca = new Movie(
                "Casablanca", "Michael Curtiz",
                1942, false, 102, "Drama"
        );

        psycho = new Movie(
                "Psycho", "Alfred Hitchcock",
                1960, false, 109, "Horror"
        );

        jaws = new Movie(
                "Jaws", "Steven Spielberg",
                1975, true, 124, "Thriller"
        );

        controller = new Controller();

        controller.addMovie(
                "Psycho", "Alfred Hitchcock",
                1960, false, 109, "Horror"
        );

        controller.addMovie(
                "Jaws", "Steven Spielberg",
                1975, true, 124, "Thriller"
        );

        controller.addMovie(
                "Casablanca", "Michael Curtiz",
                1942, false, 102, "Drama"
        );
    }

    @Test
    void titleComparatorSortsAlphabetically() {
        TitleComparator comparator = new TitleComparator();

        assertTrue(comparator.compare(casablanca, jaws) < 0);
        assertTrue(comparator.compare(psycho, jaws) > 0);
        assertEquals(0, comparator.compare(casablanca, casablanca));
    }

    @Test
    void titleComparatorIgnoresCase() {
        TitleComparator comparator = new TitleComparator();

        Movie lowercase = new Movie(
                "casablanca", "Michael Curtiz",
                1942, false, 102, "Drama"
        );

        assertEquals(0, comparator.compare(casablanca, lowercase));
    }

    @Test
    void directorComparatorSortsAlphabetically() {
        DirectorComparator comparator = new DirectorComparator();

        assertTrue(comparator.compare(psycho, casablanca) < 0);
        assertTrue(comparator.compare(jaws, casablanca) > 0);
        assertEquals(0, comparator.compare(psycho, psycho));
    }

    @Test
    void yearComparatorSortsOldestFirst() {
        YearComparator comparator = new YearComparator();

        assertTrue(comparator.compare(casablanca, psycho) < 0);
        assertTrue(comparator.compare(jaws, psycho) > 0);
        assertEquals(0, comparator.compare(psycho, psycho));
    }

    @Test
    void lengthComparatorSortsShortestFirst() {
        LengthComparator comparator = new LengthComparator();

        assertTrue(comparator.compare(casablanca, psycho) < 0);
        assertTrue(comparator.compare(jaws, psycho) > 0);
        assertEquals(0, comparator.compare(psycho, psycho));
    }

    @Test
    void genreComparatorSortsAlphabetically() {
        GenreComparator comparator = new GenreComparator();

        assertTrue(comparator.compare(casablanca, psycho) < 0);
        assertTrue(comparator.compare(jaws, psycho) > 0);
        assertEquals(0, comparator.compare(psycho, psycho));
    }

    @Test
    void movieCompareToSortsByTitle() {
        assertTrue(casablanca.compareTo(jaws) < 0);
        assertTrue(psycho.compareTo(jaws) > 0);
        assertEquals(0, casablanca.compareTo(casablanca));
    }

    @Test
    void moviesSortedByTitle() {
        ArrayList<Movie> sorted = controller.getMoviesSortedByTitle();

        assertEquals("Casablanca", sorted.get(0).getTitle());
        assertEquals("Jaws", sorted.get(1).getTitle());
        assertEquals("Psycho", sorted.get(2).getTitle());
    }

    @Test
    void sortingDoesNotChangeOriginalCollection() {
        ArrayList<Movie> original = controller.getMovies();

        controller.getMoviesSortedByTitle();
        controller.getMoviesSortedBy(SortField.YEAR);

        ArrayList<Movie> afterSorting = controller.getMovies();

        assertEquals(original, afterSorting);
        assertEquals("Psycho", afterSorting.get(0).getTitle());
        assertEquals("Jaws", afterSorting.get(1).getTitle());
        assertEquals("Casablanca", afterSorting.get(2).getTitle());
    }

    @Test
    void moviesSortedByYear() {
        ArrayList<Movie> sorted =
                controller.getMoviesSortedBy(SortField.YEAR);

        assertEquals(1942, sorted.get(0).getYearCreated());
        assertEquals(1960, sorted.get(1).getYearCreated());
        assertEquals(1975, sorted.get(2).getYearCreated());
    }
}
