package org.fatmansoft.teach.controllers.experience.achievement;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.experience.achievement.AchievementCategoryService;
import org.fatmansoft.teach.service.experience.community.CommunityCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/achievementCategory")
public class AchievementCategoryController extends AdminCommonController<AchievementCategoryService> {
}
