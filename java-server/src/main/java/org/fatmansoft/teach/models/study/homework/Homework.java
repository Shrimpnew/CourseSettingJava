package org.fatmansoft.teach.models.study.homework;
import lombok.Data;
import org.fatmansoft.teach.models.study.course.Course;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "homework_table")
@Data
public class Homework {
    @Id
    private Integer homeworkId;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @Column
    private Date homeworkDate;
    @Column
    private String homeworkDetail;
}
