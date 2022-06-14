package org.fatmansoft.teach.service.study.course;

import org.fatmansoft.teach.models.study.course.Course;
import org.fatmansoft.teach.models.study.course.CourseLog;
import org.fatmansoft.teach.repository.study.course.CourseLogRepository;
import org.fatmansoft.teach.repository.study.course.CourseRepository;
import org.fatmansoft.teach.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseLogService extends CommonService<CourseLogRepository> {
    @Autowired
    private CourseLogRepository courseLogRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List getUnselectedCourseList(Integer studentId) {
        List keywordList = new ArrayList();
        for (CourseLog courseLog : courseLogRepository.findListByKeyword("studentId", studentId)) {
            keywordList.add(courseLog.getCourse().getCourseId());
        }
        return getFullTableList(entityToId.get(Course.class),courseRepository.findListNotKeywords("courseId",keywordList));
    }
}
