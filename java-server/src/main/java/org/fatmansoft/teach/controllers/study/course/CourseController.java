package org.fatmansoft.teach.controllers.study.course;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.study.course.CourseService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/study/course")

public class CourseController extends AdminCommonController<CourseService> {

}
