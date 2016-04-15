package io.pivotal.singapore;

import io.pivotal.singapore.hello.Greeting;
import io.pivotal.singapore.hello.GreetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloSpringBootApplication {

    Logger logger = LoggerFactory.getLogger(HelloSpringBootApplication.class);

    @Autowired
    CounterService counterService;

	@Value("${greeting}")
	String greeting;

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringBootApplication.class, args);
	}

	@RequestMapping("/")
	public String hello(){
        counterService.increment("counter.services.greeting.invoked");
		return String.format("%s World!", greeting);
	}

    /** *
     * Loads the database on startup *
     * @param gr
     * @return
     */
    @Bean
    CommandLineRunner loadDatabase(GreetingRepository gr) {
        return args -> {
            logger.debug("loading database..");
            gr.save(new Greeting(1, "Hello"));
            gr.save(new Greeting(2, "Hola"));
            gr.save(new Greeting(3, "Ohai"));
            logger.debug("record count: {}", gr.count());
            gr.findAll().forEach(x -> logger.debug(x.toString()));
        };
    }
}
