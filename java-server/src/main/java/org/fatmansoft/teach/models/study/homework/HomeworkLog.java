package org.fatmansoft.teach.models.study.homework;

import lombok.Data;
import org.fatmansoft.teach.models.study.course.CourseLog;

import javax.persistence.*;

@Entity
@Table(name = "homework_log")
@Data
public class HomeworkLog {
    @Id
    private Integer homeworkLogId;
    @ManyToOne
    @JoinColumn(name = "courseLogId")
    private CourseLog courseLog;
    @ManyToOne
    @JoinColumn(name = "homeworkId")
    private Homework homework;
    @Column
    private String homeworkLogStatus;
    @Column
    private String homeworkLogDetail;
}
