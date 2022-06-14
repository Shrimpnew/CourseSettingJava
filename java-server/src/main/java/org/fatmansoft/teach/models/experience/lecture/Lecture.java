package org.fatmansoft.teach.models.experience.lecture;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "lecture")
@Data
public class Lecture {
    @Id
    private Integer lectureId;
    @Column
    private String lectureNum;
    @Column
    private String lectureName;
    @Column
    private Date lectureDate;
}
