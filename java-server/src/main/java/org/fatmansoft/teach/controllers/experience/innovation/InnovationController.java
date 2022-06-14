package org.fatmansoft.teach.controllers.experience.innovation;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.experience.innovation.InnovationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/innovation")
public class InnovationController extends CommonController<InnovationService> {
}
