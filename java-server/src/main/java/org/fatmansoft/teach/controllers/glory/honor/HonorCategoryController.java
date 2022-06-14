package org.fatmansoft.teach.controllers.glory.honor;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.glory.honor.HonorCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/glory/honorCategory")
public class HonorCategoryController extends AdminCommonController<HonorCategoryService> {
}
