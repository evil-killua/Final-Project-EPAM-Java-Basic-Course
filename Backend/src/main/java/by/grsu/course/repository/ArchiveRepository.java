package by.grsu.course.repository;

import by.grsu.course.model.Archive;
import by.grsu.course.model.Course;
import by.grsu.course.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive,Long> {
    List<Archive> findByUser(User user);
    List<Archive> findByCourse(Course course);
    Archive findByCourseAndUser(Course course, User user);

    Optional<Archive> getByCourseAndUserIsNotLike(Course course, User user);
}
