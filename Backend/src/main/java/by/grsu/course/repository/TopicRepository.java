package by.grsu.course.repository;

import by.grsu.course.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {

    List<Topic>findByTopicNameStartingWith(String name);
    Topic findByTopicName(String name);
    Optional<Topic> getByTopicName(String name);
}
