package by.grsu.course.controller;


import by.grsu.course.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{pattern}")
    public List<String> getTopicByPattern(@PathVariable String pattern) {

        return topicService.getTopicByPattern(pattern);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @PostMapping()
    public ResponseEntity<String> createTopic(@RequestBody String topicName) {

        return topicService.createTopic(topicName);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/find/{course}")
    public List<String> getTopicByCourseName(@PathVariable String course) {

        return topicService.getTopicByCourseName(course);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/passed/{id}")
    public List<String> getNotPassedCourseTopicByCourseId(@PathVariable long id) {

        return topicService.getNotPassedCourseTopicByCourseId(id);
    }

}
