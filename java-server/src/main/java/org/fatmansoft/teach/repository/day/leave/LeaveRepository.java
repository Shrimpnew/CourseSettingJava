package org.fatmansoft.teach.repository.day.leave;

import org.fatmansoft.teach.models.day.leave.Leave;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends CommonRepository<Leave,Integer> {
}
