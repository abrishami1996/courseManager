package com.microservice.courseTaking.courseManager;

import com.microservice.courseTaking.courseManager.model.Course;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class CourseManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(CourseManagerApplication.class, args);
	}


}

