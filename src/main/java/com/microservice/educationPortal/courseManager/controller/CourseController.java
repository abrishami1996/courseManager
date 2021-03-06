package com.microservice.educationPortal.courseManager.controller;

import com.microservice.educationPortal.courseManager.repository.CourseRepository;
import com.microservice.educationPortal.courseManager.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class CourseController {


    @Autowired
    CourseRepository repository;

    @Value("${AuthServiceIp}")
    private String authServiceIp;

    @Value("${AuthServicePort}")
    private String authServicePort;

    private String getRole(String jwttoken){
        final String uri = "http://"+authServiceIp+":"+authServicePort+"/getRole";
        System.out.println(jwttoken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwttoken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String s = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class).getBody().toString();
        return s.substring(1,s.length() -1);
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addCourse(String jwttoken, @RequestBody Course course) {
        if(getRole(jwttoken).equals("ROLE_ADMIN") || getRole(jwttoken).equals("ROLE_EMPLOYEE")) {
            if(course.getCode() <=0 || course.getName()==null){
                return "invalid course";
            }
            if(repository.findByCode(course.getCode())!=null){
                return "course exists";
            }
            repository.save(course);
            return "ok";
        }
        return "not authenticated";

    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public String editCourse(String jwttoken, @RequestBody Course course){
        if (getRole(jwttoken).equals("ROLE_ADMIN") || getRole(jwttoken).equals("ROLE_EMPLOYEE")) {
            Course c =repository.findByCode(course.getCode());
            if ( c!= null) {
                course.setId(c.getId());
                repository.save(course);
                return "ok";
            }
            return "Not Found";
        }
        return "not authenticated";

    }

    @RequestMapping(value = "/remove")
    public String removeCourse(String jwttoken, int courseCode){
        System.out.println("helllo");
        if (getRole(jwttoken).equals("ROLE_ADMIN") || getRole(jwttoken).equals("ROLE_EMPLOYEE")){
            if(repository.findByCode(courseCode) != null){
                repository.deleteByCode(courseCode);
                return "removed";
            }
            return "Not Found";
        }
        return "not authenticated";

    }

    @RequestMapping(value = "/exists")
    public String courseExists(Integer courseCode){
        if(repository.findByCode(courseCode) != null){
            return "ok";
        }
        else{
            return "not found";
        }
    }

    @RequestMapping(value = "/getListOfCourses")
    public List<Course> getListOfCourses(){
        return repository.findAll();
    }

}
