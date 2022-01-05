package by.grsu.course.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "topic_table")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue
    @Column(name = "topic_id")
    private Long id;

    @Column(name = "topic_name")
    private String topicName;

}
