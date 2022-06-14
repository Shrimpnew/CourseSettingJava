package org.fatmansoft.teach.controllers.experience.achievement;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.experience.achievement.AchievementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/achievement")
public class AchievementController extends CommonController<AchievementService> {
}
