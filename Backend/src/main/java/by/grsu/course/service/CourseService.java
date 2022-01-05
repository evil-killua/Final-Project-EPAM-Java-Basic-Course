package by.grsu.course.service;

import by.grsu.course.model.Course;
import by.grsu.course.model.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourse(String username);
    CourseDTO getCourseById(Long id);
    List<CourseDTO> getCourseByUserName(String username);
    List<CourseDTO> getTeacherCourseByUserName(String username);
    void createCourse(CourseDTO courseDTO);
    void deleteCourse(Long id);
    void updateCourse(CourseDTO courseDTO);
}
