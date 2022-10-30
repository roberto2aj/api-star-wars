# api-star-wars

## Requirements

In order to run this project you need:

- Docker
- make

Make already comes installed in Mac OS and Linux, but in Windows you need to install it.
You can do it [here](https://gnuwin32.sourceforge.net/packages/make.htm).

## How to run

Download/clone this repository and enter it in the terminal.

In order to build, execute `make build`. On Linux/Mac OS, depending on your configurations, 
you might need to type `sudo make build`. This will download the project dependencies, build a
jar and use it do build a Docker image.

In order to run it, execute `make run`. Once again, depending on your configurations, you might
need to type `sudo` before the command.
