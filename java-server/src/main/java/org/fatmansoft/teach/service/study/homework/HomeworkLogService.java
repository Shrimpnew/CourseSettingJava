package org.fatmansoft.teach.service.study.homework;

import org.fatmansoft.teach.models.study.course.Course;
import org.fatmansoft.teach.models.study.course.CourseLog;
import org.fatmansoft.teach.models.study.homework.Homework;
import org.fatmansoft.teach.models.study.homework.HomeworkLog;
import org.fatmansoft.teach.repository.study.homework.HomeworkLogRepository;
import org.fatmansoft.teach.repository.study.homework.HomeworkRepository;
import org.fatmansoft.teach.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeworkLogService extends CommonService<HomeworkLogRepository> {
    @Autowired
    private HomeworkLogRepository homeworkLogRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;

    public List getUnselectedHomeworkList(Integer courseId,Integer courseLogId) {
        List keywordList = new ArrayList();
        for (HomeworkLog homeworkLog : homeworkLogRepository.findListByKeyword("courseLogId", courseLogId)) {
            keywordList.add(homeworkLog.getHomework().getHomeworkId());
        }
        return getFullTableList(entityToId.get(Course.class),homeworkRepository.findListByNotKeyword("courseId",courseId,"homeworkId",keywordList));
    }
}
