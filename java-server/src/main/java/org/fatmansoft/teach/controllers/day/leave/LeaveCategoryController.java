package org.fatmansoft.teach.controllers.day.leave;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.day.leave.LeaveCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day/leaveCategory")
public class LeaveCategoryController extends AdminCommonController<LeaveCategoryService> {
}
