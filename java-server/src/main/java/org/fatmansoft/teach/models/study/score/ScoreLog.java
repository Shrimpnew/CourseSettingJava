package org.fatmansoft.teach.models.study.score;
import lombok.Data;
import org.fatmansoft.teach.models.study.course.CourseLog;

import javax.persistence.*;

@Entity
@Table(name = "score_log")
@Data
public class ScoreLog {
    @Id
    private Integer scoreLogId;
    @ManyToOne
    @JoinColumn(name = "courseLogId")
    private CourseLog courseLog;
    @Column
    private String scoreLogDetail;
    @Column
    private String scoreLogDetailMedium;
    @Column
    private String scoreLogDetailFinal;
    @Column
    private String scoreLogDay;
}
