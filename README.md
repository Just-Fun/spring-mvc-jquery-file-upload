# Files parser

Application parse files in parallel way. Amount of file parsing processes limited to 3.

Application with two REST endpoints. First one receive several files to process within request. 
Second one  return result of parsing.

Result of parsing is just a list of lines that were received in all files 
with the number of occurrences of the string in all files.

## Getting Started


### Installing

In project we use PostgreSQL database.

Create DATABASE (you can change `database name`):

``` 
CREATE DATABASE upload;
```

Create a “files” table.

```
CREATE TABLE files(
     id SERIAL PRIMARY KEY,
     name CHAR(50) NOT NULL,
     file BYTEA,
     session BIGINT
)
```
  
Create a “results” table.

```
CREATE TABLE results (
    id  SERIAL PRIMARY KEY,
    session BIGINT,
    result BYTEA
) 
```

### Prerequisites

1. Change `userName` and `password` in properties file(`src/main/resources/config.properties`).
If you changed `database name` during creating database also change `databaseName`.

```
userName = userName of your DB
password = password of your DB
database = upload (or created databaseName)
```

## Running the tests

`src/test/java` 

Run -> Run 'All Tests'

## Running project on your local machine

Maven projects -> Plugins -> jetty -> jetty:run

`http://localhost:8080/spring-mvc-jquery-file-upload/`

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Jetty](http://www.eclipse.org/jetty/) - Web server and javax.servlet container


## Authors

* **Sergii Zagryvyi** - [Just-Fun](https://github.com/Just-Fun)

