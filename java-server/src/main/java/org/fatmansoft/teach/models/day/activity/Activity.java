package org.fatmansoft.teach.models.day.activity;
import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activity")
@Data
public class Activity {
    @Id
    private Integer activityId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "activityCategoryId")
    private ActivityCategory activityCategory;
    @Column
    private String activityName;
    @Column
    private Date activityDate;
    @Column
    private String activityPlace;
    @Column
    private String activityDetail;
}
