package org.fatmansoft.teach.models.experience.practice;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "practice")
@Data
public class Practice {
    @Id
    private Integer practiceId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @Column
    private String practiceName;
    @Column
    private Date practiceDate;
    @Column
    private String practicePlace;
    @Column
    private String practiceDetail;
}
