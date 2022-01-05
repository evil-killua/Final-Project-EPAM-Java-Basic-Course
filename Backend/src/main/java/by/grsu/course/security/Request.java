package by.grsu.course.security;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Request {

    private long id;
    private String userName;
    private String userPwd;
    private List<String> roles;
}
