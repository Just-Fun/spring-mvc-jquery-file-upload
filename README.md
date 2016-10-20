# Files parser

Application parse files in parallel way. Amount of file parsing processes limited to 3.

Application with two REST endpoints.First one receive several files to process within request. 
Second one  return result of parsing.
Result of parsing is just a list of lines that were received in all files 
with the number of occurrences of the string in all files.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

1. Change ```userName``` and ```password``` in properties file(```src/main/resources/config.properties```).
You can also can change ```databaseName```(not necessary).

```
userName = userName of your DB
password = password of your DB
```

### Installing

In project we use PostgreSQL database.

Create 2 tables:
``` CREATE TABLE files(
         id SERIAL PRIMARY KEY,
         name CHAR(50) NOT NULL,
         file BYTEA,
         session BIGINT
       )
       
       CREATE TABLE results (
         id  SERIAL PRIMARY KEY,
         session BIGINT,
         result BYTEA
       ) 
```


A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Jetty](http://www.eclipse.org/jetty/) - Web server and javax.servlet container


## Authors

* **Sergii Zagryvyi** - [Just-Fun](https://github.com/Just-Fun)

