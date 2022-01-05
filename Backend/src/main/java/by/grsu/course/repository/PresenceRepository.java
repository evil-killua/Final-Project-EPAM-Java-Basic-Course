package by.grsu.course.repository;

import by.grsu.course.model.CourseTopic;
import by.grsu.course.model.Presence;
import by.grsu.course.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresenceRepository extends JpaRepository<Presence,Long> {
    Optional<Presence> getByCourseTopicAndUser(CourseTopic courseTopic, User user);
    Optional<List<Presence>> getByCourseTopic(CourseTopic courseTopic);
}
