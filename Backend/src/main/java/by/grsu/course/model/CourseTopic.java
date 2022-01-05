package by.grsu.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "course_topic")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseTopic {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "dateOfStudy", columnDefinition = "TIMESTAMP")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dateOfStudy;
}
