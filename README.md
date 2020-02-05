Address Book - PWC Coding Challenge
---
## Problem Statement

You have been asked to develop an address book that allows a user to store (between
successive runs of the program) the name and phone numbers of their friends, with the
following functionality:
- To be able to display the list of friends and their corresponding phone numbers sorted
by their name.
- Given another address book that may or may not contain the same friends, display the
list of friends that are unique to each address book (the union of all the relative
complements). 

    For example given:
    - Book1 = { “Bob”, “Mary”, “Jane” }
    - Book2 = { “Mary”, “John”, “Jane” }

        The friends that are unique to each address book are: Book1 \ Book2 = { “Bob”, “John” }


## System Requirements
The application needs the following in order to execute successfully
- Java 8
- Docker
- Gradle
- Make

## Building and Running

This project uses `gradle` for dependency management and `make` for building & running the application. It also needs `docker` for starting up the persistence layer(Redis).
 
#### Build - Compiles, runs unit tests and packages the api
1. Using Makefile `make build`
2. Using Gradle `./gradlew build`

#### Running the application
1. Using Makefile `make run`
2. Starting components individually
    * `docker-compose up -d` - to start the redis container and the redis-commander container 
    * `./gradlew build` - to start the application

## Components

1. Java Application, written in Java8
2. Redis - persistence layer to store the address books and the contacts. The redis container is started using the appendonly flag set to `true` so that the container volume survives restarts.
3. Redis Commander - To visualise the entries stored in Redis.

