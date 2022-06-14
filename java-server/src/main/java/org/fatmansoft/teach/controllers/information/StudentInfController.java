package org.fatmansoft.teach.controllers.information;

import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.information.StudentInfRepository;
import org.fatmansoft.teach.service.information.StudentInfService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/information/studentInf")
public class StudentInfController extends CommonController<StudentInfService> {

    @Autowired
    StudentInfRepository studentInfRepository;
    @Autowired
    StudentInfService studentInfService;

    @Override
    public DataResponse editInit(@Valid @RequestBody DataRequest dataRequest) {
        Integer studentInfId = dataRequest.getInteger("studentInfId");
        if(studentInfId == null && dataRequest.getInteger("studentId") != null){
            studentInfId = studentInfRepository.findEntityByKeyword("studentId",dataRequest.getString("studentId")).getStudentInfId();
        }
        return CommonMethod.getReturnData(studentInfService.getMapWithSelectOption(studentInfId));
    }
}
