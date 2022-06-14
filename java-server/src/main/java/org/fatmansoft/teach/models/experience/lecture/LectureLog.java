package org.fatmansoft.teach.models.experience.lecture;

import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;

@Entity
@Table(name = "lecture_log")
@Data
public class LectureLog {
    @Id
    private Integer lectureLogId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;
    @Column
    private String lectureLogDetail;
}
