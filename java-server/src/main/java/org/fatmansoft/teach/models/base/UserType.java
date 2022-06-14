package org.fatmansoft.teach.models.base;

import lombok.Data;
import org.fatmansoft.teach.models.base.EUserType;

import javax.persistence.*;

@Entity
@Table(name = "user_type")
@Data
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EUserType name;

    public UserType() {

    }

    public UserType(EUserType name) {
        this.name = name;
    }

}