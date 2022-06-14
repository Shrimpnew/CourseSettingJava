package org.fatmansoft.teach.models.experience.community;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "community_category")
@Data
public class CommunityCategory {
    @Id
    private Integer communityCategoryId;
    @Column
    private String communityCategoryName;
}
