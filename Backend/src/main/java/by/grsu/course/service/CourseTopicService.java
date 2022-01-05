package by.grsu.course.service;

import by.grsu.course.model.dto.CourseTopicDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CourseTopicService {
    void create(List<CourseTopicDTO> courseTopicDTOList);
    void updateCourseTopic(CourseTopicDTO courseTopicDTO);
    String getDateOfStudyByCourseNameAndTopicName(String courseName, String topicName);
    List<CourseTopicDTO> getCourseTopicByCourseName(String courseName);
}
