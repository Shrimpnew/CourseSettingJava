package org.fatmansoft.teach.models.day.activity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activity_category")
@Data
public class ActivityCategory {
    @Id
    private Integer activityCategoryId;
    @Column
    private String activityCategoryName;
}
