package io.pivotal.singapore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloSpringBootApplication {

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
}
