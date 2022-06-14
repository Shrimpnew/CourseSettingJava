package org.fatmansoft.teach.models.study.course;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_table")
@Data
public class Course {
    @Id
    @Column
    private Integer courseId;
    @Column
    private String courseName;
    @Column
    private String courseNum;
    @Column
    private String coursePoint;
    @Column
    private String coursePeriod;
    @Column
    private String courseTeacher;
}
