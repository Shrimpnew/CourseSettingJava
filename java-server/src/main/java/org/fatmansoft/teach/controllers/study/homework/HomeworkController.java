package org.fatmansoft.teach.controllers.study.homework;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.study.homework.HomeworkService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/study/homework")
public class HomeworkController extends AdminCommonController<HomeworkService> {

}
