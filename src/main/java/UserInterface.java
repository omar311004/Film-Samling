import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Controller controller;

    public UserInterface() {
        scanner = new Scanner(System.in);
        controller = new Controller();
    }

    public void startProgram() {
        System.out.println("Velkommen til min filmsamling!");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1. Opret en film");
            System.out.println("2. Vis alle film");
            System.out.println("3. Søg efter film");
            System.out.println("0. Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "0" -> running = false;
                default -> System.out.println("Ukendt valg: " + choice);
            }
        }

        System.out.println("Tak for nu – farvel!");
    }
    private void searchMovies() {
        String searchText = readText("Søg efter titel: ");

        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning.");
        } else {
            System.out.println(matches.size() + " film matcher \"" + searchText + "\":");

            for (Movie movie : matches) {
                System.out.println();
                System.out.println(movie.getTitle() + " (" + movie.getYearCreated() + ")");
                System.out.println("Instruktør: " + movie.getDirector());
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Længde: " + movie.getLengthInMinutes() + " minutter");
                System.out.println(movie.isInColor() ? "Farver" : "Sort-hvid");
            }
        }
    }
    private void showAllMovies() {
        ArrayList<Movie> movies = controller.getMovies();

        if (movies.isEmpty()) {
            System.out.println("Der er ingen film i samlingen.");
        } else {
            for (Movie movie : movies) {
                System.out.println();
                System.out.println(movie.getTitle() + " (" + movie.getYearCreated() + ")");
                System.out.println("Instruktør: " + movie.getDirector());
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Længde: " + movie.getLengthInMinutes() + " minutter");
                System.out.println("Farver: " + (movie.isInColor() ? "Farver" : "Sort-hvid"));
            }
        }
    }
    private void createMovie() {
        String title = readText("Titel: ");
        String director = readText("Instruktør: ");
        int yearCreated = readInt("Årstal: ");
        boolean inColor = readText("I farver (ja/nej): ").equalsIgnoreCase("ja");
        int lengthInMinutes = readInt("Længde i minutter: ");
        String genre = readText("Genre: ");

        controller.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        System.out.println(title + " er tilføjet til samlingen.");
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }
}
