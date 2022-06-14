package org.fatmansoft.teach.models.study.course;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;

@Entity
@Table(name = "course_log")
@Data
public class CourseLog {
    @Id
    private Integer courseLogId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
//    @Column
//    private Integer courseId;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
}
