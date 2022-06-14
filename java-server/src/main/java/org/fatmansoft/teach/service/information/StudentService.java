package org.fatmansoft.teach.service.information;

import org.fatmansoft.teach.models.information.Student;
import org.fatmansoft.teach.repository.information.StudentRepository;
import org.fatmansoft.teach.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService extends CommonService<StudentRepository> {

    @Autowired
    private StudentRepository studentRepository;

    public Integer getStudentByUsername(String userName){
        if(userName == null) return null;
        Student student = studentRepository.findEntityByKeyword("userName",userName);
        if(student == null) return null;
        return student.getStudentId();
    }
}
