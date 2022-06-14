package org.fatmansoft.teach.controllers.day.consume;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.day.consume.ConsumeCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day/consumeCategory")
public class ConsumeCategoryController extends AdminCommonController<ConsumeCategoryService> {
}
