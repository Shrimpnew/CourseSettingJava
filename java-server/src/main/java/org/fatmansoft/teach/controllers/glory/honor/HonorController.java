package org.fatmansoft.teach.controllers.glory.honor;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.glory.honor.HonorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/glory/honor")
public class HonorController extends CommonController<HonorService> {
}
