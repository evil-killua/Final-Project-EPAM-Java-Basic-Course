package by.grsu.course.repository;

import by.grsu.course.model.Role;
import by.grsu.course.model.User;
import by.grsu.course.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    List<UserRole> findByUser(User user);
    List<UserRole> findByRole(Role role);
    UserRole findByRoleAndUser(Role role,User user);
}
