FURPS – vores filmsamling
Dette dokument beskriver de funktionelle krav og kvalitetskrav til vores Filmsamling-projekt.
Kategori
Krav
Opfyldt?
Hvor i koden?
F – Functionality
Brugeren skal kunne søge efter film ved hjælp af en del af titlen, uanset store og små bogstaver.
Ja – implementeret i koden
MovieCollection.searchMovies()
U – Usability
Programmet skal vise en nummereret menu, og ugyldige menuvalg skal give en fejlbesked uden at afslutte programmet.
Ja – implementeret i koden
UserInterface.startProgram()
R – Reliability
Programmet må ikke gå ned, hvis brugeren indtaster bogstaver i stedet for et tal.
Nej – mangler fejlhåndtering
UserInterface.readInt()
P – Performance
Programmet skal kunne søge blandt 100 film på under 1 sekund.
Ikke kontrolleret – kræver måling
MovieCollection.searchMovies()
S – Supportability
Filmfunktionerne skal være adskilt fra brugerfladen, så logikken kan testes uden at starte menuen.
Ja – implementeret i klassestrukturen
MovieCollection, Controller og UserInterface
Yderligere funktionalitet
Filmsamlingen indeholder også følgende funktioner:
•	Oprette og vise film.
•	Redigere eksisterende film.
•	Slette film efter bekræftelse.
•	Markere en film som set med dagens dato.
•	Vise datoen for, hvornår en film sidst blev set.
•	Finde film, der aldrig er set, eller som ikke er set i mere end et bestemt antal dage.
Disse funktioner er implementeret i Movie, MovieCollection, Controller og UserInterface, men skal afprøves for at bekræfte, at de fungerer korrekt.
Konklusion
Filmsamlingen opfylder flere af vores FURPS-krav på kodeniveau. Vi har implementeret de centrale funktioner og organiseret programmet i separate klasser.
Der mangler fortsat forbedringer af fejlhåndteringen, og performancekravet skal måles, før det kan markeres som opfyldt.