package org.fatmansoft.teach.models.glory.honor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "honor_category")
@Data
public class HonorCategory {
    @Id
    private Integer honorCategoryId;
    @Column
    private String honorCategoryName;
}
