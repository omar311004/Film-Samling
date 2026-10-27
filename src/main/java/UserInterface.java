import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
            System.out.println("6. Marker en film som set");
            System.out.println("7. Vis film, der ikke er set længe");
            System.out.println("0. Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
                case "6" -> markMovieAsWatched();
                case "7" -> showMoviesNotWatchedForDays();
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
                showLastWatched(movie);
            }
        }
    }
    private void createMovie() {
        String title = readText("Titel: ");
        String director = readText("Instruktør: ");
        int yearCreated = readInt("Årstal: ");
        boolean inColor = readYesNo("I farver (ja/nej): ");
        int lengthInMinutes = readInt("Længde i minutter: ");
        String genre = readText("Genre: ");

        try {
            controller.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        } catch (IllegalArgumentException e) {
            System.out.println("Fejl: " + e.getMessage());
            return;
        }
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

        int choice = readInt("Vælg filmens nummer (0 = fortryd): ");

        while (choice < 0 || choice > matches.size()) {
            System.out.println("Ugyldigt nummer. Prøv igen.");
            choice = readInt("Vælg filmens nummer (0 = fortryd): ");
        }

        if (choice == 0) {
            System.out.println("Redigering annulleret.");
            return;
        }

        Movie movie = matches.get(choice - 1);

        System.out.println("Tryk Enter for at beholde den gamle værdi.");

        String title = readText("Titel (" + movie.getTitle() + "): ");
        if (title.isEmpty()) title = movie.getTitle();

        String director = readText("Instruktør (" + movie.getDirector() + "): ");
        if (director.isEmpty()) director = movie.getDirector();

        String yearText = readText("Årstal (" + movie.getYearCreated() + "): ");
        int year;

        if (yearText.isEmpty()) {
            year = movie.getYearCreated();
        } else {
            while (true) {if (yearText.isEmpty()) {
                year = movie.getYearCreated();
                break;
            }
                try {
                    year = Integer.parseInt(yearText);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ugyldigt årstal. Prøv igen.");
                    yearText = readText("Årstal: ");
                }
            }
        }

        String colorText = readText("Farver (ja/nej, Enter = behold): ");
        boolean inColor = movie.isInColor();

        if (!colorText.isEmpty()) {
            while (!colorText.equalsIgnoreCase("ja") &&
                    !colorText.equalsIgnoreCase("j") &&
                    !colorText.equalsIgnoreCase("nej") &&
                    !colorText.equalsIgnoreCase("n")) {

                System.out.println("Svar ja eller nej.");
                colorText = readText("Farver (ja/nej): ");
            }

            inColor = colorText.equalsIgnoreCase("ja") ||
                    colorText.equalsIgnoreCase("j");
        }

        String lengthText = readText("Længde (" + movie.getLengthInMinutes() + "): ");
        int length;

        if (lengthText.isEmpty()) {
            length = movie.getLengthInMinutes();
        } else {
            while (true) { if (lengthText.isEmpty()) {
                length = movie.getLengthInMinutes();
                break;
            }
                try {
                    length = Integer.parseInt(lengthText);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ugyldig filmlængde. Prøv igen.");
                    lengthText = readText("Længde: ");
                }
            }
        }

        String genre = readText("Genre (" + movie.getGenre() + "): ");
        if (genre.isEmpty()) genre = movie.getGenre();

        boolean edited;

        try {
            edited = controller.editMovie(movie, title, director, year, inColor, length, genre);
        } catch (IllegalArgumentException e) {
            System.out.println("Fejl: " + e.getMessage());
            return;
        }

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

        int choice = readInt("Vælg filmens nummer (0 = fortryd): ");

        while (choice < 0 || choice > matches.size()) {
            System.out.println("Ugyldigt nummer. Prøv igen.");
            choice = readInt("Vælg filmens nummer (0 = fortryd): ");
        }

        if (choice == 0) {
            System.out.println("Sletning annulleret.");
            return;
        }

        Movie movie = matches.get(choice - 1);

        boolean confirmation = readYesNo(
                "Er du sikker på, at du vil slette " + movie.getTitle() + "? (ja/nej): "
        );

        if (confirmation) {
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
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\"" + input + "\" er ikke et helt tal. Prøv igen.");
            }
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            String answer = readText(prompt);

            if (answer.equalsIgnoreCase("ja") || answer.equalsIgnoreCase("j")) {
                return true;
            }

            if (answer.equalsIgnoreCase("nej") || answer.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Svar ja eller nej.");
        }
    }

    private void markMovieAsWatched() {
        System.out.print("Søg efter filmens titel: ");
        String searchText = scanner.nextLine();

        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film fundet.");
            return;
        }

        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTitle());
        }

        int choice = readInt("Vælg filmens nummer (0 = fortryd): ");

        while (choice < 0 || choice > matches.size()) {
            System.out.println("Ugyldigt nummer. Prøv igen.");
            choice = readInt("Vælg filmens nummer (0 = fortryd): ");
        }

        if (choice == 0) {
            System.out.println("Annulleret.");
            return;
        }

        Movie movie = matches.get(choice - 1);
        LocalDate date = readDate("Hvornår så du filmen? (dd-MM-yyyy, Enter = i dag): ");

        try {
            controller.markMovieAsWatched(movie, date);
        } catch (IllegalArgumentException e) {
            System.out.println("Fejl: " + e.getMessage());
            return;
        }
        System.out.println(movie.getTitle() + " er markeret som set.");
    }

    private void showMoviesNotWatchedForDays() {
        int days = readInt("Hvor mange dage? ");

        ArrayList<Movie> movies = controller.getMoviesNotWatchedForDays(days);

        if (movies.isEmpty()) {
            System.out.println("Ingen film fundet.");
            return;
        }

        for (Movie movie : movies) {
            System.out.println(movie.getTitle());
            showLastWatched(movie);
        }
    }

    private void showLastWatched(Movie movie) {
        if (movie.hasBeenWatched()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            String date = movie.getLastWatched().format(formatter);
            long days = movie.getDaysSinceWatched(java.time.LocalDate.now());

            System.out.println("Sidst set: " + date + " (for " + days + " dage siden)");
        } else {
            System.out.println("Sidst set: aldrig");
        }
    }
    private LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            String input = readText(prompt);

            if (input.isEmpty()) {
                return LocalDate.now();
            }

            try {
                LocalDate date = LocalDate.parse(input, formatter);

                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Datoen må ikke ligge i fremtiden. Prøv igen.");
                    continue;
                }

                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig dato. Brug formatet dd-MM-yyyy.");
            }
        }
    }
}
