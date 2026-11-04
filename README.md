# Filmsamling

Et Java-program til at administrere en personlig filmsamling.

## Funktioner

Programmet giver brugeren mulighed for at:
- Tilføje, søge efter, redigere og slette film.
- Vise alle film i alfabetisk rækkefølge efter titel.
- Sortere film efter titel, instruktør, årstal, længde eller genre.
- Gemme og indlæse filmsamlingen fra en CSV-fil.

## Del 9 – Sortering

Klassen `Movie` implementerer `Comparable<Movie>`, så film kan sorteres alfabetisk efter titel uden forskel på store og små bogstaver.

Programmet indeholder fem Comparator-klasser:
- `TitleComparator`
- `DirectorComparator`
- `YearComparator`
- `LengthComparator`
- `GenreComparator`

Brugeren kan vælge sortering via menuvalg 8.

Sorteringen udføres på en kopi af filmsamlingen, så den oprindelige rækkefølge ikke ændres.

## Tests

Projektet bruger JUnit 5 til automatiske tests af blandt andet film, filmsamling, filhåndtering og sortering.

Alle 25 tests består.

## Projektstruktur

- `domainmodel` – film, filmsamling, controller og sortering.
- `datasource` – indlæsning og gemning af film.
- `ui` – brugergrænsefladen.
- `docs` – klassediagram.