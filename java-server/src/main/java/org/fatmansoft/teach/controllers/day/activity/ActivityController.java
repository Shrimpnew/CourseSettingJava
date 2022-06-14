package org.fatmansoft.teach.controllers.day.activity;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.day.activity.ActivityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day/activity")
public class ActivityController extends CommonController<ActivityService> {
}
