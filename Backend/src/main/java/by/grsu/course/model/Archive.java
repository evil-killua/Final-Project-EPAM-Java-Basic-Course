package by.grsu.course.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "archive_table")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Archive {

    @Id
    @GeneratedValue
    @Column(name = "archive_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_of_entry", columnDefinition = "TIMESTAMP")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dateOfEntry;

    @Column(name = "graduation_grade")
    private int graduationGrade;

}
