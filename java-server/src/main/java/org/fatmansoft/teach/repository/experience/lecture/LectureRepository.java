package org.fatmansoft.teach.repository.experience.lecture;

import org.fatmansoft.teach.models.experience.lecture.Lecture;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends CommonRepository<Lecture,Integer> {
}
