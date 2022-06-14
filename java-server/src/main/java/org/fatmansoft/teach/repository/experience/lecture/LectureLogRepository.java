package org.fatmansoft.teach.repository.experience.lecture;

import org.fatmansoft.teach.models.experience.lecture.LectureLog;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureLogRepository extends CommonRepository<LectureLog,Integer> {
}
