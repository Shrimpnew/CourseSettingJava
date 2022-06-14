package org.fatmansoft.teach.models.base;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"),
        })
@Data
public class User {
    @Id
    private Integer userId;

    @ManyToOne()
    @JoinColumn(name = "user_type_id")
    private UserType userType;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private Integer loginCount;
    private Date lastLoginTime;

    public User() {
    }

    public User(String username, String password) {
        this.userName = username;
        this.password = password;
    }

}
