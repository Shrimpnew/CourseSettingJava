package org.fatmansoft.teach.controllers.experience.community;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.experience.community.CommunityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/community")
public class CommunityController extends CommonController<CommunityService> {
}
