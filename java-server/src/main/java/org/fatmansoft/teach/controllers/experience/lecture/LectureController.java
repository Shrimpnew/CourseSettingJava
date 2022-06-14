package org.fatmansoft.teach.controllers.experience.lecture;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.experience.lecture.LectureService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/lecture")
public class LectureController extends AdminCommonController<LectureService> {
}
