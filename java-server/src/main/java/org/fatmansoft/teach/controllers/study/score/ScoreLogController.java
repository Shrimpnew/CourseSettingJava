package org.fatmansoft.teach.controllers.study.score;

import org.fatmansoft.teach.controllers.base.ReadonlyCommonController;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.study.course.CourseLogService;
import org.fatmansoft.teach.service.study.score.ScoreLogService;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/study/scoreLog")
public class ScoreLogController extends ReadonlyCommonController<ScoreLogService> {
    @Autowired
    private ScoreLogService scoreLogService;
    @Autowired
    private CourseLogService courseLogService;

    @PostMapping("/introduceInit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse introduceInit(@Valid @RequestBody DataRequest dataRequest) {
        List datalist = new ArrayList();
        List list = new ArrayList();
        List<Map> courseLogList = courseLogService.getListByKeyword("studentId", dataRequest.getInteger("studentId"));
        for (Map courseLog : courseLogList) {
            List<Map> scoreLogList = scoreLogService.getListByKeyword("courseLogId", courseLog.get("courseLogId"));
            for (Map scoreLog : scoreLogList) {
                scoreLog.putAll(courseLog);
            }
            list.addAll(scoreLogList);
        }
        datalist.add(new Pair<>(null,list));
        return CommonMethod.getReturnData(datalist);
    }
}
