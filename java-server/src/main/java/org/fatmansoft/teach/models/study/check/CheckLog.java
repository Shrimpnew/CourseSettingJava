package org.fatmansoft.teach.models.study.check;
import lombok.Data;
import org.fatmansoft.teach.models.study.course.CourseLog;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "check_log")
@Data
public class CheckLog {
    @Id
    private Integer checkLogId;
    @ManyToOne
    @JoinColumn(name = "courseLogId")
    private CourseLog courseLog;
    @Column
    private Date checkLogDate;
    @Column
    private String checkLogDetail;
    @Column
    private String checkLogSituation;
}
