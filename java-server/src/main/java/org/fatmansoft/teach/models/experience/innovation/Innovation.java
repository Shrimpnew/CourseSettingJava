package org.fatmansoft.teach.models.experience.innovation;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "innovation")
@Data
public class Innovation {
    @Id
    private Integer innovationId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @Column
    private String innovationName;
    @Column
    private Date innovationDate;
    @Column
    private String innovationDetail;
}
