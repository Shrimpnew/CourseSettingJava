package org.fatmansoft.teach.controllers.experience.lecture;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.experience.lecture.LectureLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/lectureLog")
public class LectureLogController extends CommonController<LectureLogService> {
}
