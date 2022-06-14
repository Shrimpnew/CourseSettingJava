package org.fatmansoft.teach.models.day.consume;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "consume_category")
@Data
public class ConsumeCategory {
    @Id
    private Integer consumeCategoryId;
    @Column
    private String consumeCategoryName;
}
