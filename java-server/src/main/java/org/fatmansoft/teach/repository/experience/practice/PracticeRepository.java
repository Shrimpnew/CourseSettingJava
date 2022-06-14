package org.fatmansoft.teach.repository.experience.practice;

import org.fatmansoft.teach.models.experience.practice.Practice;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRepository extends CommonRepository<Practice,Integer> {
}
