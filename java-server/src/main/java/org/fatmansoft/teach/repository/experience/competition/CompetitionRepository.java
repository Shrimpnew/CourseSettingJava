package org.fatmansoft.teach.repository.experience.competition;

import org.fatmansoft.teach.models.experience.competition.Competition;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends CommonRepository<Competition,Integer> {
}
