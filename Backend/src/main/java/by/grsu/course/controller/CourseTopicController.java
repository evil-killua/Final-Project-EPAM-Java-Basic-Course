package by.grsu.course.controller;


import by.grsu.course.model.dto.CourseTopicDTO;
import by.grsu.course.service.CourseTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/courseTopic")
public class CourseTopicController {

    @Autowired
    private CourseTopicService courseTopicService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @PostMapping()
    public ResponseEntity<String> createCourse(@RequestBody List<CourseTopicDTO> courseTopicDTOList) {

        courseTopicService.create(courseTopicDTOList);

        return new ResponseEntity<String>("courseTopic successfully created", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @PutMapping()
    public ResponseEntity<String> updateCourseTopic(@RequestBody CourseTopicDTO courseTopicDTO) {

        courseTopicService.updateCourseTopic(courseTopicDTO);

        return new ResponseEntity<String>("course topic successfully update", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/date/{course}/{topic}")
    public ResponseEntity<String> getDateOfStudyByCourseNameAndTopicName(@PathVariable String course, @PathVariable String topic) {

        String date = courseTopicService.getDateOfStudyByCourseNameAndTopicName(course, topic);

        return new ResponseEntity<String>(date, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{course}")
    public List<CourseTopicDTO> getCourseTopicByCourseName(@PathVariable String course) {

        return courseTopicService.getCourseTopicByCourseName(course);
    }
}
