# api-star-wars

This project is a small API that accesses [swapi](https://swapi.dev/) and saves part of its data into a local database for later access.

This project was written using:
- Java 17
- Maven for dependency management
- JUnit 5 for unit testing
- Mockito for mocking
- JaCoCo for generating test coverage reports
- logback (and jackson) for structured logging
- H2 for database (embedded!)
- make for build and run automation

I choose not to use Lombok or StructMap as they usually come up with the need for specific IDE configuration so I opted for easiness of configuration.

## Requirements

In order to run this project you need:

- Jdk 17
- maven
- make

Make already comes installed in Mac OS and Linux, but in Windows you need to install it.
You can do it [here](https://gnuwin32.sourceforge.net/packages/make.htm).

## How to run

Download/clone this repository and enter it in the terminal.

In order to build the application, execute `make build`. This will download some dependencies, run unit tests, generate a test report and build the application into a .jar.

The .jar will be in new directory called "apistarwars" together with the test reports, which will be in a directory called `test-reports`.

In order to run it, execute `make run`. You could also cd directly into the application directory and execute `java -jar ApiStarWars.jar`.

The logs will be saved in JSON format into a directory called `logs`.

## Database

This application uses an embedded H2 database. The data is saved into the `database` directory.

## How to use

When the application is running, you can access it on `localhost:8080/swagger-ui.html`.

There you will see an UI which will enable you to interact more easily with the API.

You will also have access to the API documentation, with data such as summary of each endpoint and schema of the data returned.

## Request and response examples

### Load planet into the database

Request (a valid planet):
```
method: PUT
url: localhost:8080/planets/10
```

Response:
```
status: 200
content:
{
  "name": "Kamino",
  "climate": "temperate",
  "terrain": "ocean",
  "films": [
    {
      "title": "Attack of the Clones",
      "director": "George Lucas",
      "releaseDate": "2002-05-16"
    }
  ]
}
```

Request (an invalid planet):
```
method: PUT
url: localhost:8080/planets/100
```

Response [1] :
```
status: 404
```

### Find planet by name/id

Request (an existing planet):
```
method: GET
url (id): localhost:8080/planets/name/10
url (name): localhost:8080/planets/name/Kamino
```
Response:
```
status: 200
content:
{
  "name": "Kamino",
  "climate": "temperate",
  "terrain": "ocean",
  "films": [
    {
      "title": "Attack of the Clones",
      "director": "George Lucas",
      "releaseDate": "2002-05-16"
    }
  ]
}
```

Request (a nonexistent planet):
```
method: GET
url (id): localhost:8080/planets/name/777
url (name): localhost:8080/planets/name/Tangamandapio
```
Response[1]:
```
status: 404
```

### Listing every planet in the database

Request:
```
method: GET
url: localhost:8080/planets/
```
Response:
```
statusCode: 200
content:
[
  {
    "name": "Alderaan",
    "climate": "temperate",
    "terrain": "grasslands, mountains",
    "films": [
      {
        "title": "A New Hope",
        "director": "George Lucas",
        "releaseDate": "1977-05-25"
      },
      {
        "title": "Revenge of the Sith",
        "director": "George Lucas",
        "releaseDate": "2005-05-19"
      }
    ]
  },
  {
    "name": "Endor",
    "climate": "temperate",
    "terrain": "forests, mountains, lakes",
    "films": [
      {
        "title": "Return of the Jedi",
        "director": "Richard Marquand",
        "releaseDate": "1983-05-25"
      }
    ]
  },
  {
    "name": "Kamino",
    "climate": "temperate",
    "terrain": "ocean",
    "films": [
      {
        "title": "Attack of the Clones",
        "director": "George Lucas",
        "releaseDate": "2002-05-16"
      }
    ]
  }
]

```

### Deleting a planet that exists in the database.

Request:
```
method: DELETE
url: localhost:8080/planets/1
```
Response:
```
statusCode: 204 (NO_CONTENT)
```

### Deleting a planet that doesn't exist in the database.

Request:
```
method: DELETE
url: localhost:8080/planets/1
```
Response:
```
statusCode: 204 (NO_CONTENT)
```

[1] I decided to return status code 404 (not found) instead of 204 (no content) because it's the same behavior as swapi.
