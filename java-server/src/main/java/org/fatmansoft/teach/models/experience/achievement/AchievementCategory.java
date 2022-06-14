package org.fatmansoft.teach.models.experience.achievement;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "achievement_category")
@Data
public class AchievementCategory {
    @Id
    private Integer achievementCategoryId;
    @Column
    private String achievementCategoryName;
}
