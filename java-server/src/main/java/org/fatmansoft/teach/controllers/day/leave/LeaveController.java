package org.fatmansoft.teach.controllers.day.leave;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.day.leave.LeaveService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day/leave")
public class LeaveController extends CommonController<LeaveService> {
}
