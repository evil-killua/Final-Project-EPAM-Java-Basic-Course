package by.grsu.course.controller;

import by.grsu.course.model.dto.ArchiveDTO;
import by.grsu.course.model.dto.CourseDTO;
import by.grsu.course.model.dto.UserDTO;
import by.grsu.course.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping()
    public List<ArchiveDTO> getAllArchive() {

        return archiveService.getAllArchive();
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{id}")
    public ArchiveDTO getArchiveById(@PathVariable long id) {

        return archiveService.getArchiveById(id);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArchive(@PathVariable long id) {

        archiveService.deleteArchive(id);

        return new ResponseEntity<String>("archive successfully deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @DeleteMapping("/delete/{username}/{courseName}")
    public ResponseEntity<String> deleteArchiveByUserName(@PathVariable String username, @PathVariable String courseName) {

        archiveService.deleteArchiveByCourseAndUserName(courseName, username);

        return new ResponseEntity<String>("you successfully left the course", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @PutMapping()
    public ResponseEntity<String> updateArchive(@RequestBody List<ArchiveDTO> archiveDTO) {

        archiveService.updateArchive(archiveDTO);

        return new ResponseEntity<String>("archive successfully deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','USER')")
    @PostMapping()
    public ResponseEntity<String> createArchive(@RequestBody ArchiveDTO archiveDTO) {

        archiveService.addNewArchive(archiveDTO);

        return new ResponseEntity<String>("you have successfully registered", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/all/{course}")
    public List<ArchiveDTO> getArchiveByCourseName(@PathVariable String course) {

        return archiveService.getArchiveByCourseName(course);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{course}/{username}")
    public ArchiveDTO getArchiveByCourseNameAndUserName(@PathVariable String course, @PathVariable String username) {

        return archiveService.getArchiveByCourseNameAndUserName(course, username);
    }

}
