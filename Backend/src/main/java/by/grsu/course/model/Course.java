package by.grsu.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "course_table")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User teacher;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "description")
    private String description;

    @Column(name = "start_of_courses", columnDefinition = "TIMESTAMP")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate startOfCourses;

    @Column(name = "end_of_courses", columnDefinition = "TIMESTAMP")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate endOfCourses;

}
