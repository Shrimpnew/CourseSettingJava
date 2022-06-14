package org.fatmansoft.teach.repository.day.activity;

import org.fatmansoft.teach.models.day.activity.ActivityCategory;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityCategoryRepository extends CommonRepository<ActivityCategory,Integer> {
}
