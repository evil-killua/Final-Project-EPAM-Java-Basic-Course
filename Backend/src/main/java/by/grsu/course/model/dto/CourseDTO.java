package by.grsu.course.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CourseDTO {
    private long id;
    private String teacherName;
    private String courseName;
    private String description;
    private String startOfCourses;
    private String endOfCourses;
}
