package me.roybailey.springboot.neo4j.bootstrap;

import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.neo4j.service.Neo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Service
public class Neo4jMovieLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Neo4jService neo4jService;

    private static AtomicBoolean loaded = new AtomicBoolean();

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        synchronized (loaded) {
            if (!loaded.get()) {
                loadMoviesWithCypher();
                loaded.set(true);
            }
        }
    }

    private void loadMoviesWithCypher() {

        if (neo4jService.setupEmbeddedDatabase()) {
            log.info("Loaded movie database sample into embedded database");
        }
    }
}
