package org.fatmansoft.teach.controllers.study.course;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.study.course.CourseLogService;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/study/courseLog")

public class CourseLogController extends CommonController<CourseLogService> {
    @Autowired
    private CourseLogService courseLogService;
    @Override
    public DataResponse editInit(@Valid @RequestBody DataRequest dataRequest) {
        List dataList = new ArrayList();
        dataList.add(new Pair<>(null,courseLogService.getUnselectedCourseList(dataRequest.getInteger("studentId"))));
        return CommonMethod.getReturnData(dataList);
    }
}
