package org.fatmansoft.teach.controllers.study.homework;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.study.course.CourseLogService;
import org.fatmansoft.teach.service.study.homework.HomeworkLogService;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/study/homeworkLog")
public class HomeworkLogController extends CommonController<HomeworkLogService> {
    @Autowired
    private HomeworkLogService homeworkLogService;


    @PostMapping("/selectInit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse selectInit(@Valid @RequestBody DataRequest dataRequest) {
        List dataList = new ArrayList();
        dataList.add(new Pair<>(null,homeworkLogService.getUnselectedHomeworkList(dataRequest.getInteger("courseId"),dataRequest.getInteger("courseLogId"))));
        return CommonMethod.getReturnData(dataList);
    }
}
