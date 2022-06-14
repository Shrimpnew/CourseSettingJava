package org.fatmansoft.teach.repository.experience.achievement;

import org.fatmansoft.teach.models.experience.achievement.AchievementCategory;
import org.fatmansoft.teach.models.experience.community.CommunityCategory;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementCategoryRepository extends CommonRepository<AchievementCategory,Integer> {
}
