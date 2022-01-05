package by.grsu.course.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseTopicDTO {

    private String courseName;
    private String topicName;
    private String dateOfStudy;
}
