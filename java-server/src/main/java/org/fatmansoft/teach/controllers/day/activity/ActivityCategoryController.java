package org.fatmansoft.teach.controllers.day.activity;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.day.activity.ActivityCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day/activityCategory")
public class ActivityCategoryController extends AdminCommonController<ActivityCategoryService> {
}
