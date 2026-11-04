# Klassediagram – Filmsamling

```mermaid
classDiagram
    namespace ui {
        class UserInterface
    }
    namespace domainmodel {
        class Controller
        class MovieCollection
        class Movie
    }
    namespace datasource {
        class FileHandler
    }

    UserInterface --> Controller
    Controller --> MovieCollection
    MovieCollection --> Movie
    Controller --> FileHandler
    FileHandler ..> Movie
```