package by.grsu.course.service.impl;

import by.grsu.course.exception.TopicAlreadyExistException;
import by.grsu.course.model.Course;
import by.grsu.course.model.Topic;
import by.grsu.course.repository.CourseRepository;
import by.grsu.course.repository.CourseTopicRepository;
import by.grsu.course.repository.TopicRepository;
import by.grsu.course.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTopicRepository courseTopicRepository;

    @Override
    public List<String> getTopicByPattern(String pattern) {

        log.info("Fetching topics by pattern:{}", pattern);
        List<String> topicList = new ArrayList<>();

        topicRepository.findByTopicNameStartingWith(pattern).forEach(topic -> {
            topicList.add(topic.getTopicName());
        });

        return topicList;
    }

    @Override
    public ResponseEntity<String> createTopic(String topicName) {

        log.info("saving new topic: {}", topicName);
        Optional<Topic> optionalTopic = topicRepository.getByTopicName(topicName);

        if (!optionalTopic.isPresent()) {

            Topic topic = Topic.builder()
                    .topicName(topicName)
                    .build();

            topicRepository.save(topic);
            return new ResponseEntity<String>("topic successfully created", HttpStatus.OK);
        }
        else{
            log.error("topic already exists");
            throw new TopicAlreadyExistException("topic already exists");
        }

    }

    @Override
    public List<String> getTopicByCourseName(String courseName) {

        log.info("Fetching topic by topic name: {}", courseName);
        Course course = courseRepository.getByCourseName(courseName);
        List<String> topicList = new ArrayList<>();

        courseTopicRepository.getByCourse(course).forEach(courseTopic -> {
            topicList.add(courseTopic.getTopic().getTopicName());
        });

        return topicList;
    }

    @Override
    public List<String> getNotPassedCourseTopicByCourseId(long id) {

        Course course = courseRepository.getById(id);
        List<String> topicList = new ArrayList<>();

        log.info("Fetching not passed topics in course: {}", course.getCourseName());
        courseTopicRepository.getByCourseAndDateOfStudy(course, null).forEach(courseTopic -> {
            topicList.add(courseTopic.getTopic().getTopicName());
        });
        return topicList;
    }
}
