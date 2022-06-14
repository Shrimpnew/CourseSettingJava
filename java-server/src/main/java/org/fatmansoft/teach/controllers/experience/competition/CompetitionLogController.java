package org.fatmansoft.teach.controllers.experience.competition;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.service.experience.competition.CompetitionLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/competitionLog")
public class CompetitionLogController extends CommonController<CompetitionLogService> {
}
