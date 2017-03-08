package me.roybailey.springboot.neo4j.controller;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.neo4j.domain.Movie;
import me.roybailey.springboot.neo4j.repository.MovieRepository;
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
@RequestMapping(value = "/movie/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository repo) {
        this.movieRepository = repo;
    }


    @ResponseBody
    @GetMapping(path = "/movies")
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
    @PostMapping(path = "/movie/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findOne(id);
        return ResponseEntity.ok(movie);
    }


    @ResponseBody
    @PostMapping(path = "/movie-update")
    public ResponseEntity<?> updateMovie(@RequestBody(required = true) Movie updates) {
        Movie movie = movieRepository.findOne(Long.valueOf(updates.getId()));
        if (updates.getTitle() != null) {
            movie.setTitle(updates.getTitle());
        }
        if (updates.getReleased() != null) {
            movie.setReleased(updates.getReleased());
        }
        if (updates.getStars() != null) {
            movie.setStars(updates.getStars());
        }
        movieRepository.save(movie);
        return ResponseEntity.ok(movie);
    }
}
