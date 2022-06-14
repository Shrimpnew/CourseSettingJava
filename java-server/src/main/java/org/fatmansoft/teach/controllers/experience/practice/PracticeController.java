package org.fatmansoft.teach.controllers.experience.practice;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.experience.practice.PracticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/practice")
public class PracticeController extends CommonController<PracticeService> {
}
