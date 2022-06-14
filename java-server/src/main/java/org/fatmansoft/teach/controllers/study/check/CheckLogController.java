package org.fatmansoft.teach.controllers.study.check;

import org.fatmansoft.teach.controllers.base.ReadonlyCommonController;
import org.fatmansoft.teach.service.study.check.CheckLogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study/checkLog")
public class CheckLogController extends ReadonlyCommonController<CheckLogService> {

}
