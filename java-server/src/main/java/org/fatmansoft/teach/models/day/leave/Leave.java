package org.fatmansoft.teach.models.day.leave;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave")
@Data
public class Leave {
    @Id
    private Integer leaveId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "leaveCategoryId")
    private LeaveCategory leaveCategory;
    @Column
    private String leaveName;
    @Column
    private Date leaveDate;
    @Column
    private String leaveDetail;
    @Column
    private String certifier;
}
