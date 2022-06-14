package org.fatmansoft.teach.controllers.experience.community;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.experience.community.CommunityCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/communityCategory")
public class CommunityCategoryController extends AdminCommonController<CommunityCategoryService> {
}
