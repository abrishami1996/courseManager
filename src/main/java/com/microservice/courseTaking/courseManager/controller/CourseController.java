package com.microservice.courseTaking.courseManager.controller;

import com.microservice.courseTaking.courseManager.model.Course;
import com.microservice.courseTaking.courseManager.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CourseController {

    @Autowired
    CourseRepository repository;


    private String getRole(String jwttoken){
        final String uri = "http://172.21.50.142:8080/getRole";
        System.out.println(jwttoken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwttoken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String s = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class).getBody().toString();
        return s;
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addCourse(String jwttoken, @RequestBody Course course) {
        if(getRole(jwttoken).equals("ROLE_ADMIN")) {
            repository.save(course);
            return "ok";
        }
        return "not authenticated";

    }

    @RequestMapping(value = "/edit")
    public String editCourse(String jwttoken, @RequestBody Course course){
        if (getRole(jwttoken).equals("ROLE_ADMIN")) {
            if (repository.existsById(course.getId())) {
                repository.save(course);
                return "ok";
            }
            return "Not Found";
        }
        return "not authenticated";

    }

    @RequestMapping(value = "/remove")
    public String removeCourse(String jwttoken, @RequestBody int id){
        if (getRole(jwttoken).equals("ROLE_ADMIN")){
            if(repository.existsById(id)){
                repository.deleteById(id);
                return "removed";
            }
            return "Not Found";
        }
        return "not authenticated";

    }



}
