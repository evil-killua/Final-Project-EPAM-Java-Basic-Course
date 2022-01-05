package by.grsu.course.repository;

import by.grsu.course.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
    User findByFirstNameAndLastName(String firstName,String lastName);
    Optional<User> findById(Long id);
}
