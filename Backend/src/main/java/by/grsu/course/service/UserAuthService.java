package by.grsu.course.service;

import by.grsu.course.model.Role;
import by.grsu.course.model.User;
import by.grsu.course.model.UserRole;
import by.grsu.course.model.dto.UserDTO;
import by.grsu.course.repository.RoleRepository;
import by.grsu.course.repository.UserRepository;
import by.grsu.course.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserAuthService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username).get();

        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        List<GrantedAuthority> grantedAuthorities = userRoles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getRole().getRoleName());
        }).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(username, user.getUserPass(), grantedAuthorities);
    }

    public void saveUser(UserDTO request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            log.error("User already exists");
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .userName(request.getUserName())
                .userPass(passwordEncoder.encode(request.getUserPwd()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        List<UserRole> roles = request.getRoles().stream().map(r -> {
            Role role = roleRepository.findRoleByRoleName(r);
            return UserRole.builder()
                    .role(role)
                    .user(user)
                    .build();
        }).collect(Collectors.toList());

        userRepository.save(user);
        roles.forEach(r -> userRoleRepository.save(r));

    }
}
