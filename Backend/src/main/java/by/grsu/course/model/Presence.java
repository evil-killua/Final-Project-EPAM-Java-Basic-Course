package by.grsu.course.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "presence_table")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Presence {

    @Id
    @GeneratedValue
    @Column(name = "presence_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_topic_id")
    private CourseTopic courseTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="user_presence")
    private boolean userPresence;
}
