package com.in28minute.database.databasedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DatabaseDemoApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonJdbcDao dao;

	public static void main(String[] args) {
		SpringApplication.run(DatabaseDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("All users -> {}", dao.findAll());
		logger.info("User with id 10001 -> {}", dao.findById(10001));
		logger.info("Delete user 10002: -> {}", dao.deleteById(10002));

		logger.info("Insert user 10004 -> {}", dao.insert(new Person(10004, "Tara", "Berlin", new Date())));

		logger.info("Update 10004 -> {}", dao.update(new Person(10003, "Pieter", "Utrech", new Date())));
	}
}

