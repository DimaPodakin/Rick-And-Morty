## Rick-and-Morty
This is a web application in which you can get information about the characters from the cartoon series Rick-and-Morty.
In total, the application contains 4 endpoints:
1. get one character randomly - `http://localhost:6868/movie-character/random`
2. get characters whose names contain a certain parameter - `http://localhost:6868/movie-character/random/by-name?name=Rick`
3. get character by id - `http://localhost:6868/movie-character/1`
4. get all characters - `http://localhost:6868/movie-character`

The application supports pagination and sorting, so you can set your own parameters for displaying characters:
`http://localhost:6868/movie-character?count=20&page=5&sort=name:DESC`

Or you can just use swagger: `http://localhost:6868/swagger-ui/#/`

### Used technologies:
+ Java
+ Spring Boot
+ DB PostgreSQL
+ Apache Maven
+ Docker

### Launching the project locally:
1. Pull this file;
2. Install and start Docker; 
3. Open terminal and insert command: docker -compose up; 
4. Open browser in this url: `http://localhost:6868/swagger-ui/#/`
5. The program is running😊