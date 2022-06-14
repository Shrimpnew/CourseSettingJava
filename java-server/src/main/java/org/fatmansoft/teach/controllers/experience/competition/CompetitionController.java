package org.fatmansoft.teach.controllers.experience.competition;

import org.fatmansoft.teach.controllers.base.AdminCommonController;
import org.fatmansoft.teach.service.experience.competition.CompetitionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/competition")
public class CompetitionController extends AdminCommonController<CompetitionService> {
}
