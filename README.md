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
    * `java -jar build/libs/*.jar` - to start the application

## Components

1. Java Application, written in Java8
2. Redis - persistence layer to store the address books and the contacts. The redis container is started using the appendonly flag set to `true` so that the container volume survives restarts.
3. Redis Commander - To visualise the entries stored in Redis.

## Usage of the App

On running the app, you will get the following menu

```Address Book Main Menu
   Choose from the following commands: [list | create | add | contacts | compare | exit]
   ------------------------------------------------------------------------------------------------------------------
       list                            list all books
       create                          create a new book
       add                             add a contact to a book
       contacts                        list all contacts in a book
       compare                         Compare 2 books to list the contacts that are unique to each address book'
       exit                            exit the program
   ------------------------------------------------------------------------------------------------------------------
```

- On chosing `list`, you will have to enter a book name which exists
- On chosing `create`, you will have to enter a book name that does not already exist
- `add` lets you create a contact inside a book
- `contacts` lets you view the contacts inside a book
- `compare` lets you compare the contents of 2 address books that exist
- `exit` is pretty self explanatory, exits the program
