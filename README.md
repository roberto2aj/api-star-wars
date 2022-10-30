# api-star-wars

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

The .jar will be in new directory called "apistarwars" together with the test reports, which will be in a directory called "test-reports".

In order to run it, execute `make run`. You could also cd directly into the application directory and execute `java -jar ApiStarWars.jar`.

The logs will be saved in JSON format into a directory called `logs`.

## About the database

This application uses an embedded H2 database. The data is saved into the `database` directory. 