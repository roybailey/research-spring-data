package me.roybailey.springboot.neo4j.controller;

import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.neo4j.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class Neo4jMovieControllerTest {

    private static class MovieList extends ArrayList<Movie>
    {}

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testMovieAPI_AllMovies() {
        ResponseEntity<MovieList> entity =
                this.restTemplate.getForEntity("/movie/v1/movies", MovieList.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Movie> response = entity.getBody();
        assertThat(response).hasSize(38);
    }

/*
    @Test
    public void testMovieAPI_MovieUpdate() {

        // first we will search for The Matrix by title to obtain current values
        String url = apiMovieSearch("The Matrix", "1");
        log.info("Requesting movie-search url={}", url);
        ResponseEntity<MovieList> entityList = this.restTemplate.getForEntity(url, MovieList.class);
        Movie theMatrix = entityList
                .getBody()
                .getData()
                .get(0);

        // now we can construct/trigger a business event resource
        url = apiMovieUpdate();
        MovieListDto movieUpdateDto = MovieListDto.builder()
                .data(ImmutableList.of(Movie.builder()
                        .id(theMatrix.getId())
                        .title(theMatrix.getTitle() + " Updated")
                        .released(theMatrix.getReleased() - 10)
                        .stars(5)
                        .build()))
                .build();
        log.info("Requesting movie update url={}", url);
        ResponseEntity<MovieList> entity =
                this.restTemplate.postForEntity(url, movieUpdateDto, MovieList.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Movie response = entity.getBody().get(0);
        assertThat(response.getId()).isEqualTo(theMatrix.getId());
        assertThat(response.getTitle()).isEqualTo(theMatrix.getTitle() + " Updated");
        assertThat(response.getReleased()).isEqualTo(theMatrix.getReleased() - 10);
        assertThat(response.getStars()).isEqualTo(5);
    }
*/
}
