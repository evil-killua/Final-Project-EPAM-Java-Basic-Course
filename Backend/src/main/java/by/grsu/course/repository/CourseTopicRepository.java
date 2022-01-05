package by.grsu.course.repository;

import by.grsu.course.model.Course;
import by.grsu.course.model.CourseTopic;
import by.grsu.course.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseTopicRepository extends JpaRepository<CourseTopic,Long> {
    List<CourseTopic> getByCourse(Course course);
    CourseTopic getByCourseAndTopic(Course course, Topic topic);
    List<CourseTopic> getByCourseAndDateOfStudy(Course course, LocalDate dateOfStudy);
}
