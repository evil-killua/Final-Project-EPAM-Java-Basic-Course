package by.grsu.course.repository;

import by.grsu.course.model.Course;
import by.grsu.course.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Course getById(long id);
    void deleteById(long id);
    Course getByCourseName(String courseName);
    Optional<Course> findByCourseName(String courseName);
    List<Course> getByTeacher(User user);

    List<Course> getByTeacherNot(User user);
    List<Course> getByTeacherIsNotLike(User user);
}
