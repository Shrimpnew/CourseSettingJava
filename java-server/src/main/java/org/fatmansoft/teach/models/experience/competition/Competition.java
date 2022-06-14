package org.fatmansoft.teach.models.experience.competition;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "competition")
@Data
public class Competition {
    @Id
    private Integer competitionId;
    @Column
    private String competitionNum;
    @Column
    private String competitionName;
    @Column
    private Date competitionDate;
}
