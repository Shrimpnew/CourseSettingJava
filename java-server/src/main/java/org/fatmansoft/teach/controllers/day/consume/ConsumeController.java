package org.fatmansoft.teach.controllers.day.consume;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.day.consume.ConsumeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day/consume")
public class ConsumeController extends CommonController<ConsumeService> {
}
