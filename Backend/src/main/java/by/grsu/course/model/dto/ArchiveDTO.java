package by.grsu.course.model.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ArchiveDTO {
    private Long id;
    private String courseName;
    private String userName;
    private String dateOfEntry;
    private int graduationGrade;
}
