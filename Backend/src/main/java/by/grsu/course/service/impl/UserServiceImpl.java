package by.grsu.course.service.impl;

import by.grsu.course.model.Course;
import by.grsu.course.model.Role;
import by.grsu.course.model.User;
import by.grsu.course.model.UserRole;
import by.grsu.course.model.dto.UserDTO;
import by.grsu.course.repository.*;
import by.grsu.course.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserDTO> getAllUsers() {

        log.info("Fetching all users");
        List<UserDTO> users = new ArrayList<>();

        userRepository.findAll().forEach(user -> {
            List<String> roles = new ArrayList<>();
            userRoleRepository.findByUser(user).forEach(r -> {
                roles.add(r.getRole().getRoleName());
            });

            UserDTO dto = UserDTO.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .id(user.getId())
                    .phone(user.getPhone())
                    .userName(user.getUserName())
                    .userPwd(user.getUserPass())
                    .roles(roles)
                    .build();
            users.add(dto);

        });

        return users;
    }

    @Override
    public UserDTO getUserById(long id) {

        log.info("Fetching user by id:{}", id);
        User user = userRepository.findById(id).get();
        List<String> userRoleList = new ArrayList<>();
        userRoleRepository.findByUser(user).stream().forEach(r -> {
            userRoleList.add(r.getRole().getRoleName());
        });

        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userPwd(user.getUserPass())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(userRoleList)
                .build();

    }

    @Override
    public void deleteUser(long id) {

        log.info("deleting user by id:{}", id);
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(UserDTO userDTO) {

        log.info("updating user:{}", userDTO.getUserName());
        User user = userRepository.findById(userDTO.getId()).get();

        List<String> before = new ArrayList<>();
        List<String> after = userDTO.getRoles();

        userRoleRepository.findByUser(user).forEach(userRole -> {
            before.add(userRole.getRole().getRoleName());
        });

        List<String> similar = new ArrayList<>(before);
        List<String> different = new ArrayList<>();
        different.addAll(before);
        different.addAll(after);

        similar.retainAll(after);
        different.removeAll(similar);

        similar.forEach(sim -> {
            before.remove(sim);
            after.remove(sim);
        });

        if (before.size() == 0) {

            for (String roleName : after) {
                Role role = roleRepository.findRoleByRoleName(roleName);
                UserRole userRole = UserRole.builder()
                        .role(role)
                        .user(user)
                        .build();
                userRoleRepository.save(userRole);
            }

        } else if (after.size() == 0) {

            for (String roleName : before) {
                Role role = roleRepository.findRoleByRoleName(roleName);
                UserRole userRole = userRoleRepository.findByRoleAndUser(role, user);
                userRoleRepository.delete(userRole);
            }

        } else {

            for (String roleName : before) {
                Role role = roleRepository.findRoleByRoleName(roleName);
                UserRole userRole = userRoleRepository.findByRoleAndUser(role, user);
                userRoleRepository.delete(userRole);
            }

            for (String roleName : after) {
                Role role = roleRepository.findRoleByRoleName(roleName);
                UserRole userRole = UserRole.builder()
                        .role(role)
                        .user(user)
                        .build();
                userRoleRepository.save(userRole);
            }

        }

        user.setUserPass(userDTO.getUserPwd());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getUserByCourseName(String courseName) {

        log.info("Fetching all users by course name:{}", courseName);
        Course course = courseRepository.getByCourseName(courseName);

        List<UserDTO> userList = new ArrayList<>();

        archiveRepository.findByCourse(course).forEach(archive -> {

            User user = archive.getUser();
            List<String> roles = new ArrayList<>();
            userRoleRepository.findByUser(user).forEach(role -> {
                roles.add(role.getRole().getRoleName());
            });

            UserDTO userDTO = UserDTO.builder()
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userPwd(user.getUserPass())
                    .userName(user.getUserName())
                    .id(user.getId())
                    .roles(roles)
                    .build();

            userList.add(userDTO);
        });

        return userList;
    }

    @Override
    public List<String> getTeacher() {

        log.info("Fetching all teachers");
        Role role_teacher = roleRepository.findRoleByRoleName("ROLE_TEACHER");
        List<String> userList = new ArrayList<>();

        userRoleRepository.findByRole(role_teacher).forEach(userRole -> {
            User user = userRole.getUser();

            userList.add(user.getFirstName().concat(" ").concat(user.getLastName()));
        });

        return userList;
    }
}
