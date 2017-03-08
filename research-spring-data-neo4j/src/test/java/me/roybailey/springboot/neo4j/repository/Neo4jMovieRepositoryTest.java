package me.roybailey.springboot.neo4j.repository;

import me.roybailey.springboot.neo4j.domain.Movie;
import me.roybailey.springboot.neo4j.domain.Person;
import me.roybailey.springboot.neo4j.service.Neo4jService;
import org.assertj.core.api.ListAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class Neo4jMovieRepositoryTest {

    private Logger LOG = LoggerFactory.getLogger(Neo4jMovieRepositoryTest.class);

    @Autowired
    Neo4jService neo4jService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;


    @Before
    public void setupDatabase() {
        neo4jService.setupEmbeddedDatabase();
    }


    @Test
    public void testNeo4jMovieRepository() {

        List<Movie> allMovies = StreamSupport.stream(movieRepository.findAll().spliterator(), false).collect(Collectors.toList());
        LOG.info("Matrix movie = " + allMovies);
        assertThat(allMovies).isNotNull().hasSize(38);

        Movie matrix = movieRepository.findByTitle("The Matrix");
        LOG.info("Matrix movie = " + matrix);
        assertThat(matrix).isNotNull().hasFieldOrProperty("title");

        List<Movie> ninetiesMovies = movieRepository.findMoviesBetween(1990,1999);
        LOG.info("Nineties movies = " + ninetiesMovies);
        assertThat(ninetiesMovies).isNotNull().hasSize(20);

        Set<Movie> tomHanksMovies = movieRepository.findByActorName("Tom Hanks");
        LOG.info("Tom Hanks movies = " + tomHanksMovies);
        assertThat(tomHanksMovies).isNotNull().hasSize(12);

        Set<Person> actorsInTheMatrix = movieRepository.findByTitle("The Matrix").getActors();
        LOG.info("Actors in The Matrix = " + actorsInTheMatrix);
        assertThat(actorsInTheMatrix).isNotNull().hasSize(5);

        Set<Person> actorDirectors = movieRepository.findActorDirectors();
        LOG.info("Actor Directors = " + actorDirectors);
        assertThat(actorDirectors).isNotNull().hasSize(8);
    }


    private ListAssert<Movie> assertFindMovies(
            String name,
            String filter,
            int skip,
            int limit,
            int expectedTotal
    ) {
        String cypherFilter = movieRepository.cypherLike(filter);
        int actualTotal = movieRepository.findMoviesTotal(cypherFilter);
        List<Movie> allMovies = movieRepository.findMovies(cypherFilter, skip, limit);
        LOG.info(name+" = " + allMovies);
        assertThat(actualTotal).isEqualTo(expectedTotal);
        return assertThat(allMovies);
    }

    @Test
    public void testMovieRepository_findMoviesPagination() {

        assertFindMovies("allMovies", "", 0, 10, 38).hasSize(10);
        assertFindMovies("theMoviesPage1", "the", 0, 10, 12).hasSize(10);
        assertFindMovies("theMoviesPage2", "the", 10, 10, 12).hasSize(2);
        assertFindMovies("matrixMovies", "matrix", 0, 10, 3).hasSize(3);

    }

    @Test
    public void testPersonRepository() {

        Person tomHanks = personRepository.findByName("Tom Hanks");
        LOG.info("Tom Hanks = " + tomHanks);
        assertThat(tomHanks).isNotNull().hasFieldOrPropertyWithValue("name", "Tom Hanks");
    }
}
