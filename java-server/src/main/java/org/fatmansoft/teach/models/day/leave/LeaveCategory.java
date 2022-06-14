package org.fatmansoft.teach.models.day.leave;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leave_category")
@Data
public class LeaveCategory {
    @Id
    private Integer leaveCategoryId;
    @Column
    private String leaveCategoryName;
}
