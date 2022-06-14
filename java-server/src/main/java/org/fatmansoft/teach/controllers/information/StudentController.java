package org.fatmansoft.teach.controllers.information;

import org.bouncycastle.asn1.ocsp.ResponseData;
import org.fatmansoft.teach.controllers.base.CommonController;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.information.StudentInfRepository;
import org.fatmansoft.teach.service.information.StudentService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/information/student")
public class StudentController extends CommonController<StudentService> {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/getUserStudent")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getUserStudent(@Valid @RequestBody DataRequest dataRequest){
        return CommonMethod.getReturnData(studentService.getStudentByUsername(dataRequest.getString("username")));
    }
}
