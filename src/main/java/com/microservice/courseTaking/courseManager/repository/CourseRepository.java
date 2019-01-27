package com.microservice.courseTaking.courseManager.repository;

import com.microservice.courseTaking.courseManager.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

}
