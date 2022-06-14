package org.fatmansoft.teach.models.information;

import lombok.Data;
import org.fatmansoft.teach.models.base.User;

import javax.persistence.*;

@Entity
@Table(	name = "student")
@Data
public class Student {
    @Id
    private Integer studentId;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    @Column
    private String studentNum;
    @Column
    private String studentName;
}
