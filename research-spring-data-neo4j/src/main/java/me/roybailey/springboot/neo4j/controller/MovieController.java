package me.roybailey.springboot.neo4j.controller;

import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.neo4j.domain.Movie;
import me.roybailey.springboot.neo4j.domain.Person;
import me.roybailey.springboot.neo4j.repository.MovieRepository;
import me.roybailey.springboot.neo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/neo4j/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieRepository movieRepository;

    private final PersonRepository personRepository;


    @ResponseBody
    @GetMapping(path = "/movie")
    public ResponseEntity<?> getAllMovies() {
        ResponseEntity<?> response;
        try {
            Iterable<Movie> movies = movieRepository.findAll();
            List<Movie> movieList = StreamSupport.stream(movies.spliterator(), false)
                    .collect(Collectors.toList());
            response = ResponseEntity.ok(movieList);
        } catch (Exception err) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Throwables.getStackTraceAsString(err));
        }
        return response;
    }


    @ResponseBody
    @GetMapping(path = "/movie/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findOne(id);
        return ResponseEntity.ok(movie);
    }


    @ResponseBody
    @PostMapping(path = "/movie")
    public ResponseEntity<?> updateMovie(@RequestBody(required = true) Movie updates) {
        Movie movie;
        if (updates.getId() != null) {
            movie = movieRepository.findOne(Long.valueOf(updates.getId()));
            if (updates.getTitle() != null) {
                movie.setTitle(updates.getTitle());
            }
            if (updates.getReleased() != null) {
                movie.setReleased(updates.getReleased());
            }
            if (updates.getStars() != null) {
                movie.setStars(updates.getStars());
            }
        } else {
            movie = updates;
        }
        movieRepository.save(movie);
        return ResponseEntity.ok(movie);
    }


    @ResponseBody
    @DeleteMapping(path = "/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieRepository.delete(id);
        return ResponseEntity.ok().build();
    }


    @ResponseBody
    @GetMapping(path = "/movie-between/{from}/{upto}")
    public ResponseEntity<?> getMovieBetween(@PathVariable("from") int from, @PathVariable("upto") int upto) {
        ResponseEntity<?> response;
        try {
            List<Movie> movies = movieRepository.findMoviesBetween(from, upto);
            response = ResponseEntity.ok(movies);
        } catch (Exception err) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Throwables.getStackTraceAsString(err));
        }
        return response;
    }


    @ResponseBody
    @GetMapping(path = "/person")
    public ResponseEntity<?> getAllPerson() {
        ResponseEntity<?> response;
        try {
            Iterable<Person> actors = personRepository.findAll();
            List<Person> personList = StreamSupport.stream(actors.spliterator(), false)
                    .collect(Collectors.toList());
            response = ResponseEntity.ok(personList);
        } catch (Exception err) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Throwables.getStackTraceAsString(err));
        }
        return response;
    }


    @ResponseBody
    @GetMapping(path = "/person/{id}")
    public ResponseEntity<?> getPerson(@PathVariable Long id) {
        Person person = personRepository.findOne(id);
        return ResponseEntity.ok(person);
    }


}
