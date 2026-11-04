# FURPS – vores filmsamling

Dette dokument beskriver de funktionelle krav og kvalitetskrav til vores Filmsamling-projekt.

| Kategori | Krav | Opfyldt? | Hvor i koden? |
|---|---|---|---|
| **F – Functionality** | Brugeren skal kunne søge efter film ved hjælp af en del af titlen, uanset store og små bogstaver. | Ja – implementeret i koden | `MovieCollection.searchMovies()` |
| **F – Functionality** | US14: Programmet skal kunne indlæse og gemme filmsamlingen i en CSV-fil. | Ja – implementeret og testet med JUnit | `FileHandler`, `Controller` og `FileHandlerTest` |
| **F – Functionality** | US16: "Vis alle film" viser filmene sorteret alfabetisk efter titel, uanset store og små bogstaver. | Ja – implementeret i koden | `Movie.compareTo()`, `MovieCollection.getMoviesSortedByTitle()`, `UserInterface.showAllMovies()` |
| **F – Functionality** | US17: Brugeren kan vælge at sortere filmene efter titel, instruktør, årstal, længde eller genre. Tekst sorteres alfabetisk, tal fra mindste til største. | Ja – implementeret i koden | `SortField`, `TitleComparator`, `DirectorComparator`, `YearComparator`, `LengthComparator`, `GenreComparator`, `Controller.getMoviesSortedBy()`, `UserInterface.showMoviesSorted()` |
| **U – Usability** | Programmet skal vise en nummereret menu med valgmulighederne 0–8. Hvis brugeren indtaster et valg uden for dette interval, skal programmet vise en fejlbesked og derefter vise menuen igen uden at afslutte programmet. | Ja – implementeret i koden | `UserInterface.startProgram()` |
| **U – Usability** | Sorteringsmenuen viser egenskaberne som en nummereret liste på dansk. Et ugyldigt valg giver en fejlbesked i stedet for at stoppe programmet. | Ja – implementeret i koden | `UserInterface.showMoviesSorted()` |
| **R – Reliability** | Programmet må ikke gå ned, hvis brugeren indtaster bogstaver i stedet for et tal. | Ja – implementeret med fejlhåndtering | `UserInterface.readInt()` |
| **R – Reliability** | Sortering må ikke ændre samlingens egen rækkefølge, da det er den, der gemmes i filen. Der sorteres derfor i en kopi af listen. | Ja – implementeret og testet med JUnit | `MovieCollection.getMoviesSorted()`, `ComparatorTest.sortingDoesNotChangeOriginalCollection()` |
| **P – Performance** | Programmet skal kunne søge blandt 100 film på under 1 sekund. | Ja – målt med JUnit (`assertTimeout`) | `MovieCollection.searchMovies()`, `MovieCollectionTest.searchAmong100MoviesIsFasterThanOneSecond()` |
| **S – Supportability** | Filmfunktionerne skal være adskilt fra brugerfladen, så logikken kan testes uden at starte menuen. | Ja – implementeret i klassestrukturen | `MovieCollection`, `Controller` og `UserInterface` |
| **S – Supportability** | Det skal være nemt at tilføje en ny sortering: en ny `Comparator`-klasse, en ny værdi i `SortField` og en ny `case` i `comparatorFor`. Resten af koden kender kun typen `Comparator<Movie>`. | Ja – implementeret i klassestrukturen | `Controller.comparatorFor()`, `SortField` |
| **S – Supportability** | Alle fem comparatorer, `Movie.compareTo` og sorteringen gennem `Controller` skal have unit tests. | Ja – testet med JUnit | `ComparatorTest` |

## Yderligere funktionalitet

Filmsamlingen indeholder også følgende funktioner:

* Oprette og vise film.
* Redigere eksisterende film.
* Slette film efter bekræftelse.
* Markere en film som set med dagens dato.
* Vise datoen for, hvornår en film sidst blev set.
* Finde film, der aldrig er set, eller som ikke er set i mere end et bestemt antal dage.

Disse funktioner er implementeret i `Movie`, `MovieCollection`, `Controller` og `UserInterface`, men skal afprøves for at bekræfte, at de fungerer korrekt.

## Konklusion

Filmsamlingen opfylder alle vores FURPS-krav. Vi har implementeret de centrale funktioner, organiseret programmet i separate klasser og tilføjet sortering med `Comparable` og `Comparator`. Fejlhåndteringen af input er på plads, og performancekravet er målt med en JUnit-test.
