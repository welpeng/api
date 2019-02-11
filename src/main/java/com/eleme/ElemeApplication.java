package com.eleme;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.eleme.dao")
public class ElemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElemeApplication.class, args);
	}
}
