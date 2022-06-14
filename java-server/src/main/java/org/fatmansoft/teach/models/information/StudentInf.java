package org.fatmansoft.teach.models.information;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "student_inf")
@Data
public class StudentInf {
    @Id
    private Integer studentInfId;
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @Column
    private String sex;
    @Column
    private String age;
    @Column
    private Date birthday;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String nation;
    @Column
    private String IDCard;
    @Column
    private String politicalStatus;
}
