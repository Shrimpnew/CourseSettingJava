package org.fatmansoft.teach.models.experience.achievement;

import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "achievement")
@Data
public class Achievement {
    @Id
    private Integer achievementId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "achievementCategoryId")
    private AchievementCategory achievementCategory;
    @Column
    private String achievementName;
    @Column
    private Date achievementDate;
    @Column
    private String achievementDetail;
    @Column
    private String achievementHonor;
}
