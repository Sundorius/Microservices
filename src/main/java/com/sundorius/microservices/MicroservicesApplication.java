package com.sundorius.microservices;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Log
@SpringBootApplication
public class MicroservicesApplication
{
    public static void main(String[] args) {
		SpringApplication.run(MicroservicesApplication.class, args);
	}
}
