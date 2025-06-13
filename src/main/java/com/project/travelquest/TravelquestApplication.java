package com.project.travelquest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.travelquest.map.mapper")
public class TravelquestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelquestApplication.class, args);
	}
}
