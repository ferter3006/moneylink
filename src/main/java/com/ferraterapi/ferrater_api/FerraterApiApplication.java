package com.ferraterapi.ferrater_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class FerraterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FerraterApiApplication.class, args);
	}

}

@RestController
class HelloController {
	@GetMapping("/hello")
	public String Hello(){
		return "Hola desde API Rest en SpringBot!";
	}
}
