package by.grsu.course.controller;

import by.grsu.course.model.dto.PresenceDTO;
import by.grsu.course.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/presence")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @PostMapping()
    public ResponseEntity<String> createPresence(@RequestBody List<PresenceDTO> presenceDTO) {

        presenceService.createPresence(presenceDTO);

        return new ResponseEntity<String>("presence successfully created", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{course}")
    public List<PresenceDTO> getPresenceByCourseName(@PathVariable String course) {

        return presenceService.getPresenceByCourseName(course);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{course}/{username}")
    public List<PresenceDTO> getPresenceByCourseNameAndUserName(@PathVariable String course, @PathVariable String username) {

        return presenceService.getPresenceByCourseNameAndUserName(course, username);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/get/{course}/{topic}")
    public List<PresenceDTO> getPresenceByCourseNameAndTopicName(@PathVariable String course, @PathVariable String topic) {

        return presenceService.getPresenceByCourseNameAndTopicName(course, topic);
    }


}
