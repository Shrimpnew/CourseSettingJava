package org.fatmansoft.teach.repository.day.leave;

import org.fatmansoft.teach.models.day.leave.LeaveCategory;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveCategoryRepository extends CommonRepository<LeaveCategory,Integer> {
}
