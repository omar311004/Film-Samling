import java.util.ArrayList;
public class Controller {
    private MovieCollection movieCollection;

    public Controller() {
        movieCollection = new MovieCollection();
    }

    public void addMovie(String title, String director, int yearCreated, boolean inColor,
                         int lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
    }
    public ArrayList<Movie> getMovies() {
        return movieCollection.getMovies();
    }
    public ArrayList<Movie> searchMovies(String searchText) {
        return movieCollection.searchMovies(searchText);
    }
}
