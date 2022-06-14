package org.fatmansoft.teach.models.glory.honor;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "honor")
@Data
public class Honor {
    @Id
    private Integer honorId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "honorCategoryId")
    private HonorCategory honorCategory;
    @Column
    private String honorName;
    @Column
    private Date honorDate;
    @Column
    private String honorDetail;
    @Column
    private String honorDegree;
}
