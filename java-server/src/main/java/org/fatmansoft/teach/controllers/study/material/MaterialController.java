package org.fatmansoft.teach.controllers.study.material;

import org.fatmansoft.teach.controllers.base.ReadonlyCommonController;
import org.fatmansoft.teach.service.study.material.MaterialService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study/material")
public class MaterialController extends ReadonlyCommonController<MaterialService> {

}
