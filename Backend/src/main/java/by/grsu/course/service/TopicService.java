package by.grsu.course.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {
    List<String> getTopicByPattern(String pattern);
    ResponseEntity<String> createTopic(String topicName);
    List<String> getTopicByCourseName(String courseName);
    List<String> getNotPassedCourseTopicByCourseId(long id);
}
