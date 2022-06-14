package org.fatmansoft.teach.models.day.consume;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consume")
@Data
public class Consume {
    @Id
    private Integer consumeId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "consumeCategoryId")
    private ConsumeCategory consumeCategory;
    @Column
    private String consumeName;
    @Column
    private Date consumeDate;
    @Column
    private String consumeDetail;
    @Column
    private String consumeAmount;
}
