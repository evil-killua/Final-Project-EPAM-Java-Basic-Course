package by.grsu.course.model.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PresenceDTO {

    private String courseName;
    private String topicName;
    private String  userName;
    private boolean userPresence;
}
