package org.fatmansoft.teach.models.study.material;
import lombok.Data;
import org.fatmansoft.teach.models.study.course.Course;

import javax.persistence.*;

@Entity
@Table(name = "material_table")
@Data
public class Material {
    @Id
    private Integer materialId;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @OneToOne
    @JoinColumn(name = "materialCategoryId")
    private MaterialCategory materialCategory;
    @Column
    private String materialNum;
    @Column
    private String materialName;
    @Column
    private String materialDetail;
}
