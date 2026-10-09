package io.github.kebabulon.simplesocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// TODO: remove this hack and setup docker compose with postgre
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SimplesocialApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimplesocialApplication.class, args);
	}
}
