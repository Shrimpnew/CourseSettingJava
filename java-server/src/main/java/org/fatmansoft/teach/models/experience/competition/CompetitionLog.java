package org.fatmansoft.teach.models.experience.competition;

import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;

@Entity
@Table(name = "competition_log")
@Data
public class CompetitionLog {
    @Id
    private Integer competitionLogId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "competitionId")
    private Competition competition;
    @Column
    private String competitionLogResult;
    @Column
    private String competitionLogDetail;
}
