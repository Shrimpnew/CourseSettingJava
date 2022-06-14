package org.fatmansoft.teach.models.study.material;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "material_category")
@Data
public class MaterialCategory {
    @Id
    private Integer materialCategoryId;
    @Column
    private String materialCategoryName;
}
