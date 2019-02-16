package com.microservice.educationPortal.courseManager.repository;

import com.microservice.educationPortal.courseManager.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findByid(Integer integer);
    Course findByCode(Integer code);

    @Transactional
    void deleteByCode(Integer code);
}
