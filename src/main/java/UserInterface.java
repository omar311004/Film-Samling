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
            System.out.println("4. Redigér en film");
            System.out.println("5. Slet en film");
            System.out.println("0. Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
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

    private void editMovie() {
        String searchText = readText("Søg efter film, du vil redigere: ");
        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film fundet.");
            return;
        }

        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTitle());
        }

        int choice = readInt("Vælg filmens nummer: ");

        if (choice < 1 || choice > matches.size()) {
            System.out.println("Ugyldigt nummer.");
            return;
        }

        Movie movie = matches.get(choice - 1);

        System.out.println("Tryk Enter for at beholde den gamle værdi.");

        String title = readText("Titel (" + movie.getTitle() + "): ");
        if (title.isEmpty()) title = movie.getTitle();

        String director = readText("Instruktør (" + movie.getDirector() + "): ");
        if (director.isEmpty()) director = movie.getDirector();

        String yearText = readText("Årstal (" + movie.getYearCreated() + "): ");
        int year = yearText.isEmpty() ? movie.getYearCreated() : Integer.parseInt(yearText);

        String colorText = readText("Farver (ja/nej): ");
        boolean inColor = colorText.isEmpty()
                ? movie.isInColor()
                : colorText.equalsIgnoreCase("ja");

        String lengthText = readText("Længde (" + movie.getLengthInMinutes() + "): ");
        int length = lengthText.isEmpty()
                ? movie.getLengthInMinutes()
                : Integer.parseInt(lengthText);

        String genre = readText("Genre (" + movie.getGenre() + "): ");
        if (genre.isEmpty()) genre = movie.getGenre();

        controller.editMovie(movie, title, director, year, inColor, length, genre);

        System.out.println("Filmen er opdateret:");
        System.out.println("Titel: " + movie.getTitle());
        System.out.println("Instruktør: " + movie.getDirector());
        System.out.println("Årstal: " + movie.getYearCreated());
        System.out.println("Farver: " + (movie.isInColor() ? "Ja" : "Nej"));
        System.out.println("Længde: " + movie.getLengthInMinutes() + " minutter");
        System.out.println("Genre: " + movie.getGenre());
    }


    private void deleteMovie() {
        String searchText = readText("Søg efter film, du vil slette: ");
        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film fundet.");
            return;
        }

        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTitle());
        }

        String choiceText = readText("Vælg filmens nummer: ");
        int choice;

        try {
            choice = Integer.parseInt(choiceText);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldigt nummer.");
            return;
        }

        if (choice < 1 || choice > matches.size()) {
            System.out.println("Ugyldigt nummer.");
            return;
        }

        Movie movie = matches.get(choice - 1);

        String confirmation = readText(
                "Er du sikker på, at du vil slette " + movie.getTitle() + "? (ja/nej): "
        );

        if (confirmation.equalsIgnoreCase("ja")) {
            boolean deleted = controller.deleteMovie(movie);

            if (deleted) {
                System.out.println("Filmen er slettet.");
            } else {
                System.out.println("Filmen kunne ikke slettes.");
            }
        } else {
            System.out.println("Sletning annulleret.");
        }
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
