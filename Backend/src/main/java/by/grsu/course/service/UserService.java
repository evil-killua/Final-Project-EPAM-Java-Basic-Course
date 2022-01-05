package by.grsu.course.service;

import by.grsu.course.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(long id);
    void deleteUser(long id);
    void updateUser(UserDTO userDTO);
    List<UserDTO> getUserByCourseName(String courseName);
    List<String> getTeacher();
}
