package org.fatmansoft.teach.models.experience.community;

import lombok.Data;
import org.fatmansoft.teach.models.information.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "community")
@Data
public class Community {
    @Id
    private Integer communityId;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "communityCategoryId")
    private CommunityCategory communityCategory;
    @Column
    private String communityName;
    @Column
    private Date communityDate;
    @Column
    private String communityDetail;
    @Column
    private String communityResult;
}
