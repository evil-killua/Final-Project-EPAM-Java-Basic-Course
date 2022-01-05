package by.grsu.course.service.impl;

import by.grsu.course.model.Archive;
import by.grsu.course.model.Course;
import by.grsu.course.model.User;
import by.grsu.course.model.dto.CourseDTO;
import by.grsu.course.repository.ArchiveRepository;
import by.grsu.course.repository.CourseRepository;
import by.grsu.course.repository.UserRepository;
import by.grsu.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArchiveRepository archiveRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<CourseDTO> getAllCourse(String username) {
        User user = userRepository.findByUserName(username).get();
        List<CourseDTO> courseList = new ArrayList<>();

        log.info("Fetching courses by username:{} and where the user is not a teacher and does not participate",username);
        courseRepository.getByTeacherNot(user).forEach(course -> {

            Archive archive = archiveRepository.findByCourseAndUser(course, user);

            if (archive == null) {

                CourseDTO courseDTO = CourseDTO.builder()
                        .id(course.getId())
                        .courseName(course.getCourseName())
                        .description(course.getDescription())
                        .teacherName(course.getTeacher().getFirstName().concat(" ").concat(course.getTeacher().getLastName()))
                        .startOfCourses(course.getStartOfCourses().toString())
                        .endOfCourses(course.getEndOfCourses().toString())
                        .build();
                courseList.add(courseDTO);
            }

        });

        return courseList;
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.getById(id);

        log.info("Fetching course by id: {}",id);

        return CourseDTO.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .teacherName(course.getTeacher().getFirstName().concat(" ").concat(course.getTeacher().getLastName()))
                .startOfCourses(course.getStartOfCourses().toString())
                .endOfCourses(course.getEndOfCourses().toString())
                .build();
    }

    @Override
    public void createCourse(CourseDTO courseDTO) {

        if(courseRepository.findByCourseName(courseDTO.getCourseName()).isPresent()){
            log.error("Course already exists");
            throw new RuntimeException("Course already exists");
        }

        log.info("saving new course: {}",courseDTO.getCourseName());
        String[] s = courseDTO.getTeacherName().split(" ");
        User teacher = userRepository.findByFirstNameAndLastName(s[0], s[1]);

        LocalDate start = LocalDate.parse(courseDTO.getStartOfCourses(), dateFormatter);
        LocalDate end = LocalDate.parse(courseDTO.getEndOfCourses(), dateFormatter);

        Course course = Course.builder()
                .courseName(courseDTO.getCourseName())
                .description(courseDTO.getDescription())
                .endOfCourses(end)
                .startOfCourses(start)
                .id(courseDTO.getId())
                .teacher(teacher)
                .build();

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {

        log.info("deleting course by id: {}",id);
        courseRepository.deleteById(id);
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) {

        log.info("updating course: {}",courseDTO.getCourseName());
        Course course = courseRepository.getById(courseDTO.getId());
        LocalDate start = LocalDate.parse(courseDTO.getStartOfCourses(), dateFormatter);
        LocalDate end = LocalDate.parse(courseDTO.getEndOfCourses(), dateFormatter);

        String[] s = courseDTO.getTeacherName().split(" ");
        User teacher = userRepository.findByFirstNameAndLastName(s[0], s[1]);

        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setEndOfCourses(end);
        course.setStartOfCourses(start);
        course.setTeacher(teacher);

        courseRepository.save(course);
    }

    @Override
    public List<CourseDTO> getCourseByUserName(String username) {

        log.info("Fetching all users course by username:{}",username);
        User user = userRepository.findByUserName(username).get();
        List<CourseDTO> courseList = new ArrayList<>();
        archiveRepository.findByUser(user).forEach(a -> {
            Course course = a.getCourse();

            CourseDTO courseDTO = CourseDTO.builder()
                    .courseName(course.getCourseName())
                    .description(course.getDescription())
                    .endOfCourses(course.getEndOfCourses().toString())
                    .startOfCourses(course.getStartOfCourses().toString())
                    .id(course.getId())
                    .teacherName(course.getTeacher().getFirstName().concat(" ").concat(course.getTeacher().getLastName()))
                    .build();

            courseList.add(courseDTO);

        });

        return courseList;
    }

    @Override
    public List<CourseDTO> getTeacherCourseByUserName(String username) {

        log.info("Fetching all teacher course by username:{}",username);
        User user = userRepository.findByUserName(username).get();
        List<CourseDTO> courseList = new ArrayList<>();

        courseRepository.getByTeacher(user).forEach(course -> {
            CourseDTO courseDTO = CourseDTO.builder()
                    .courseName(course.getCourseName())
                    .description(course.getDescription())
                    .startOfCourses(course.getStartOfCourses().toString())
                    .endOfCourses(course.getEndOfCourses().toString())
                    .teacherName(course.getTeacher().getFirstName().concat(" ").concat(course.getTeacher().getLastName()))
                    .id(course.getId())
                    .build();

            courseList.add(courseDTO);
        });

        return courseList;
    }
}
