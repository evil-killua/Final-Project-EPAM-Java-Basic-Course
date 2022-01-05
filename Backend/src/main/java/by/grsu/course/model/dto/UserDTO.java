package by.grsu.course.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private long id;
    private String userName;
    private String userPwd;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<String> roles;
}
