package me.roybailey.springboot;

import me.roybailey.springboot.neo4j.SpringDataNeo4jApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataNeo4jApplication.class)
public class SpringDataNeo4jApplicationTest {

	@Test
	public void contextLoads() {
	}

}
