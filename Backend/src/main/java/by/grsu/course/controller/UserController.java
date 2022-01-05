package by.grsu.course.controller;

import by.grsu.course.model.dto.UserDTO;
import by.grsu.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping()
    public List<UserDTO> getAllUser() {

        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable long id) {

        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/student/{course}")
    public List<UserDTO> getUserByCourseName(@PathVariable String course) {

        return userService.getUserByCourseName(course);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/teacher")
    public List<String> getTeacher() {

        return userService.getTeacher();
    }


    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {

        userService.deleteUser(id);

        return new ResponseEntity<String>("user successfully deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {

        userService.updateUser(userDTO);

        return new ResponseEntity<String>("user successfully update", HttpStatus.OK);
    }

}
