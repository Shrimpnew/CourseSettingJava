package org.fatmansoft.teach.repository.experience.achievement;

import org.fatmansoft.teach.models.experience.achievement.Achievement;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CommonRepository<Achievement,Integer> {
}
