package by.grsu.course.service.impl;

import by.grsu.course.model.Archive;
import by.grsu.course.model.Course;
import by.grsu.course.model.Presence;
import by.grsu.course.model.User;
import by.grsu.course.model.dto.ArchiveDTO;
import by.grsu.course.repository.*;
import by.grsu.course.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTopicRepository courseTopicRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<ArchiveDTO> getAllArchive() {
        List<ArchiveDTO> archiveList = new ArrayList<>();

        log.info("Fetching all archives");
        archiveRepository.findAll().forEach(archive -> {
            ArchiveDTO archiveDTO = ArchiveDTO.builder()
                    .courseName(archive.getCourse().getCourseName())
                    .dateOfEntry(archive.getDateOfEntry().toString())
                    .graduationGrade(archive.getGraduationGrade())
                    .id(archive.getId())
                    .userName(archive.getUser().getFirstName().concat(" ").concat(archive.getUser().getLastName()))
                    .build();

            archiveList.add(archiveDTO);

        });

        return archiveList;
    }

    @Override
    public ArchiveDTO getArchiveById(long id) {
        Archive archive = archiveRepository.findById(id).get();
        log.info("Fetching archive by id: {}", id);

        return ArchiveDTO.builder()
                .courseName(archive.getCourse().getCourseName())
                .dateOfEntry(archive.getDateOfEntry().toString())
                .graduationGrade(archive.getGraduationGrade())
                .id(archive.getId())
                .userName(archive.getUser().getFirstName().concat(" ").concat(archive.getUser().getLastName()))
                .build();
    }

    @Override
    public void deleteArchive(long id) {

        log.info("deleting archive by id: {}", id);
        archiveRepository.deleteById(id);
    }

    @Override
    public void updateArchive(List<ArchiveDTO> archiveDTOList) {

        archiveDTOList.forEach(archiveDTO -> {
            Archive archive = archiveRepository.findById(archiveDTO.getId()).get();
            log.info("updating archive");

            archive.setGraduationGrade(archiveDTO.getGraduationGrade());

            archiveRepository.save(archive);
        });

    }

    @Override
    public void addNewArchive(ArchiveDTO archiveDTO) {

        log.info("creating new archive");
        User user = userRepository.findByUserName(archiveDTO.getUserName()).get();
        Course course = courseRepository.getByCourseName(archiveDTO.getCourseName());
        LocalDate parseDate = LocalDate.parse(archiveDTO.getDateOfEntry(), dateFormatter);

        Archive archive = Archive.builder()
                .course(course)
                .dateOfEntry(parseDate)
                .graduationGrade(0)
                .user(user)
                .build();

        archiveRepository.save(archive);
    }


    @Override
    public void deleteArchiveByCourseAndUserName(String courseName, String username) {

        User user = userRepository.findByUserName(username).get();
        Course course = courseRepository.getByCourseName(courseName);

        log.info("deleting user: presence in course: {}", course.getCourseName());
        courseTopicRepository.getByCourse(course).forEach(courseTopic -> {
            Optional<Presence> presence = presenceRepository.getByCourseTopicAndUser(courseTopic, user);

            presence.ifPresent(value -> presenceRepository.delete(value));
        });

        log.info("deleting archive");
        Archive archive = archiveRepository.findByCourseAndUser(course, user);

        archiveRepository.delete(archive);
    }

    @Override
    public List<ArchiveDTO> getArchiveByCourseName(String courseName) {
        Course course = courseRepository.getByCourseName(courseName);
        List<ArchiveDTO> archiveList = new ArrayList<>();

        log.info("Fetching all archives by course name: {}", courseName);
        archiveRepository.findByCourse(course).forEach(archive -> {
            ArchiveDTO archiveDTO = ArchiveDTO.builder()
                    .userName(archive.getUser().getFirstName().concat(" ").concat(archive.getUser().getLastName()))
                    .graduationGrade(archive.getGraduationGrade())
                    .dateOfEntry(archive.getDateOfEntry().toString())
                    .courseName(archive.getCourse().getCourseName())
                    .id(archive.getId())
                    .build();

            archiveList.add(archiveDTO);
        });

        return archiveList;
    }

    @Override
    public ArchiveDTO getArchiveByCourseNameAndUserName(String courseName, String username) {
        Course course = courseRepository.getByCourseName(courseName);
        User user = userRepository.findByUserName(username).get();

        log.info("Fetching archive by course name: {} and username: {}", courseName, username);
        Archive archive = archiveRepository.findByCourseAndUser(course, user);

        return ArchiveDTO.builder()
                .id(archive.getId())
                .courseName(archive.getCourse().getCourseName())
                .dateOfEntry(archive.getDateOfEntry().toString())
                .graduationGrade(archive.getGraduationGrade())
                .userName(user.getFirstName().concat(" ").concat(user.getLastName()))
                .build();
    }
}
