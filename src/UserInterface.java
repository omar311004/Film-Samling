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
            System.out.println("0. Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createMovie();
                case "0" -> running = false;
                default -> System.out.println("Ukendt valg: " + choice);
            }
        }

        System.out.println("Tak for nu – farvel!");
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
