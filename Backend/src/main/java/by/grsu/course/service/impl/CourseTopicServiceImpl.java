package by.grsu.course.service.impl;

import by.grsu.course.model.Course;
import by.grsu.course.model.CourseTopic;
import by.grsu.course.model.Topic;
import by.grsu.course.model.dto.CourseTopicDTO;
import by.grsu.course.repository.CourseRepository;
import by.grsu.course.repository.CourseTopicRepository;
import by.grsu.course.repository.TopicRepository;
import by.grsu.course.service.CourseTopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CourseTopicServiceImpl implements CourseTopicService {

    @Autowired
    private CourseTopicRepository courseTopicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void create(List<CourseTopicDTO> courseTopicDTOList) {

        Course course = courseRepository.getByCourseName(courseTopicDTOList.get(0).getCourseName());

        courseTopicDTOList.forEach(courseTopicDTO -> {
            log.info("saving a new topic:{} in {} course",courseTopicDTO.getTopicName(),courseTopicDTO.getCourseName());

            Topic topic = topicRepository.findByTopicName(courseTopicDTO.getTopicName());
            CourseTopic courseTopic = CourseTopic.builder()
                    .course(course)
                    .topic(topic)
                    .build();

            courseTopicRepository.save(courseTopic);
        });
    }

    @Override
    public void updateCourseTopic(CourseTopicDTO courseTopicDTO) {

        log.info("updating info of topic {} in {} course",courseTopicDTO.getTopicName(),courseTopicDTO.getCourseName());
        Course course = courseRepository.getByCourseName(courseTopicDTO.getCourseName());
        Topic topic = topicRepository.getByTopicName(courseTopicDTO.getTopicName()).get();

        CourseTopic courseTopic = courseTopicRepository.getByCourseAndTopic(course, topic);

        LocalDate parseDate = LocalDate.parse(courseTopicDTO.getDateOfStudy(), dateFormatter);

        courseTopic.setDateOfStudy(parseDate);

        courseTopicRepository.save(courseTopic);
    }

    @Override
    public String getDateOfStudyByCourseNameAndTopicName(String courseName, String topicName) {

        log.info("Fetching date of study of {} topic in {} course",topicName,courseName);
        Course course = courseRepository.getByCourseName(courseName);
        Topic topic = topicRepository.findByTopicName(topicName);

        CourseTopic courseTopic = courseTopicRepository.getByCourseAndTopic(course, topic);

        if (courseTopic.getDateOfStudy() != null){
            return courseTopic.getDateOfStudy().toString();
        }else {
            return "no date";
        }
    }

    @Override
    public List<CourseTopicDTO> getCourseTopicByCourseName(String courseName) {

        log.info("Fetching topics by course name: {}",courseName);
        Course course = courseRepository.getByCourseName(courseName);
        List<CourseTopicDTO> courseTopicList = new ArrayList<>();

        courseTopicRepository.getByCourse(course).forEach(courseTopic -> {
            CourseTopicDTO courseTopicDTO = CourseTopicDTO.builder()
                    .courseName(courseTopic.getCourse().getCourseName())
                    .topicName(courseTopic.getTopic().getTopicName())
                    .build();

            if (courseTopic.getDateOfStudy()!=null){
                courseTopicDTO.setDateOfStudy(courseTopic.getDateOfStudy().toString());
            }else {
                courseTopicDTO.setDateOfStudy("no date");
            }

            courseTopicList.add(courseTopicDTO);
        });

        return courseTopicList;
    }
}
