package by.grsu.course.service.impl;

import by.grsu.course.model.*;
import by.grsu.course.model.dto.PresenceDTO;
import by.grsu.course.repository.*;
import by.grsu.course.service.PresenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PresenceServiceImpl implements PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseTopicRepository courseTopicRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createPresence(List<PresenceDTO> presenceDTOList) {

        presenceDTOList.forEach(presenceDTO -> {

            log.info("saving user:{} presence", presenceDTO.getUserName());
            String[] words = presenceDTO.getUserName().split(" ");
            User user = userRepository.findByFirstNameAndLastName(words[0], words[1]);
            Course course = courseRepository.getByCourseName(presenceDTO.getCourseName());
            Topic topic = topicRepository.getByTopicName(presenceDTO.getTopicName()).get();
            CourseTopic courseTopic = courseTopicRepository.getByCourseAndTopic(course, topic);

            Presence presence = Presence.builder()
                    .courseTopic(courseTopic)
                    .user(user)
                    .userPresence(presenceDTO.isUserPresence())
                    .build();

            presenceRepository.save(presence);
        });
    }

    @Override
    public List<PresenceDTO> getPresenceByCourseNameAndUserName(String courseName, String userName) {


        Course course = courseRepository.getByCourseName(courseName);
        User user = userRepository.findByUserName(userName).get();
        List<PresenceDTO> presenceList = new ArrayList<>();

        log.info("Fetching user presence by user name: {} and course name:{}", userName, courseName);
        courseTopicRepository.getByCourse(course).forEach(courseTopic -> {

            Optional<Presence> presence = presenceRepository.getByCourseTopicAndUser(courseTopic, user);

            if (presence.isPresent()) {
                PresenceDTO presenceDTO = PresenceDTO.builder()
                        .courseName(course.getCourseName())
                        .topicName(courseTopic.getTopic().getTopicName())
                        .userName(user.getFirstName().concat(" ").concat(user.getLastName()))
                        .userPresence(presence.get().isUserPresence())
                        .build();
                presenceList.add(presenceDTO);
            }
        });
        return presenceList;
    }

    @Override
    public List<PresenceDTO> getPresenceByCourseName(String courseName) {

        Course course = courseRepository.getByCourseName(courseName);
        List<PresenceDTO> presenceList = new ArrayList<>();
        log.info("Fetching presence by course name:{}", courseName);
        courseTopicRepository.getByCourse(course).forEach(courseTopic -> {
            Optional<List<Presence>> optionalPresenceList = presenceRepository.getByCourseTopic(courseTopic);

            optionalPresenceList.ifPresent(presences -> presences.forEach(presence -> {

                PresenceDTO presenceDTO = PresenceDTO.builder()
                        .userPresence(presence.isUserPresence())
                        .userName(presence.getUser().getFirstName().concat(" ").concat(presence.getUser().getLastName()))
                        .topicName(courseTopic.getTopic().getTopicName())
                        .courseName(course.getCourseName())
                        .build();
                presenceList.add(presenceDTO);
            }));
        });

        return presenceList;
    }

    @Override
    public List<PresenceDTO> getPresenceByCourseNameAndTopicName(String courseName, String topicName) {

        Course course = courseRepository.getByCourseName(courseName);
        Topic topic = topicRepository.findByTopicName(topicName);
        CourseTopic courseTopic = courseTopicRepository.getByCourseAndTopic(course, topic);

        List<PresenceDTO> presenceDTOList = new ArrayList<>();
        log.info("Fetching user presence by course name:{} and topic name:{}", courseName, topicName);
        Optional<List<Presence>> optionalPresenceList = presenceRepository.getByCourseTopic(courseTopic);
        optionalPresenceList.ifPresent(presences -> presences.forEach(presence -> {

            PresenceDTO presenceDTO = PresenceDTO.builder()
                    .courseName(courseName)
                    .topicName(topicName)
                    .userName(presence.getUser().getFirstName().concat(" ").concat(presence.getUser().getLastName()))
                    .userPresence(presence.isUserPresence())
                    .build();

            presenceDTOList.add(presenceDTO);
        }));
        return presenceDTOList;
    }
}
