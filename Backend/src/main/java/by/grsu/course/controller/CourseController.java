package by.grsu.course.controller;

import by.grsu.course.model.dto.CourseDTO;
import by.grsu.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @GetMapping("/all/{username}")
    public List<CourseDTO> getAllCourse(@PathVariable String username) {

        return courseService.getAllCourse(username);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {

        CourseDTO course = courseService.getCourseById(id);

        return ResponseEntity.ok(course);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @GetMapping("/view/{username}")
    public List<CourseDTO> getCourseByUserName(@PathVariable String username) {

        return courseService.getCourseByUserName(username);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @GetMapping("/teacher/{username}")
    public List<CourseDTO> getTeacherCourseByUserName(@PathVariable String username) {

        return courseService.getTeacherCourseByUserName(username);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @PostMapping()
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO courseDTO) {

        courseService.createCourse(courseDTO);

        return new ResponseEntity<String>("course successfully created", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable long id) {

        courseService.deleteCourse(id);

        return new ResponseEntity<String>("course successfully deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @PutMapping()
    public ResponseEntity<String> updateCourse(@RequestBody CourseDTO courseDTO) {

        courseService.updateCourse(courseDTO);

        return new ResponseEntity<String>("course successfully update", HttpStatus.OK);
    }

}
