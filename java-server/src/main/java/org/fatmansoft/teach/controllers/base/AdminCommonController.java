package org.fatmansoft.teach.controllers.base;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.base.CommonService;
import org.fatmansoft.teach.service.introduce.IntroduceService;
import org.fatmansoft.teach.util.ClassUtil;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.Pair;
import org.fatmansoft.teach.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminCommonController<S extends CommonService> {
    private final Class<S> sClass;

    public AdminCommonController(){
        sClass = ClassUtil.getGenericClass(getClass(),0);
    }

    @PostMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse test() {
        return CommonMethod.getReturnData("Test success!");
    }

    @PostMapping("/init")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse init(@Valid @RequestBody DataRequest dataRequest) {
        S s = SpringUtil.getBean(sClass);
        String initField = dataRequest.getString("field");
        if(initField == null) initField = s.getIdName();
        System.out.println("initField " + initField + " val = " + dataRequest.get(initField));
        List dataList = new ArrayList();
        dataList.add(new Pair<>(null,s.getListByKeyword(initField,dataRequest.get(initField))));
        System.out.println("initList: " + dataList);
        return CommonMethod.getReturnData(dataList);
    }


    @PostMapping("/query")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse query(@Valid @RequestBody DataRequest dataRequest) {
        S s = SpringUtil.getBean(sClass);
        List dataList = new ArrayList();
        String initField = dataRequest.getString("field");
        if(initField == null) initField = s.getIdName();
        List fieldList = dataRequest.getList("queryField");
        System.out.println("field = " + initField + " queryField = " + fieldList);
        dataList.add(new Pair<>(null,s.getQueryList(initField,dataRequest.get(initField),fieldList,dataRequest.getString("keyword"))));
        return CommonMethod.getReturnData(dataList);
    }


    @PostMapping("/editInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse editInit(@Valid @RequestBody DataRequest dataRequest) {
        S s = SpringUtil.getBean(sClass);
        Pair pair = s.getMapWithSelectOption(dataRequest.getInteger(s.getIdName()));
        return CommonMethod.getReturnData(pair);
    }


    @PostMapping("/editSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse editSubmit(@Valid @RequestBody DataRequest dataRequest) {
        S s = SpringUtil.getBean(sClass);
        System.out.println("request: " + dataRequest);
        System.out.println("form " + dataRequest.getMap("form"));
        Integer id = s.saveEntityToDependTable(dataRequest.getMap("form"));
        if(id < 0 )return CommonMethod.getReturnMessageError("Submit Error!");
        return CommonMethod.getReturnData(id);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse delete(@Valid @RequestBody DataRequest dataRequest) {
        S s = SpringUtil.getBean(sClass);
        s.deleteEntity(s.getIdName(),dataRequest.getInteger(s.getIdName()));
        return CommonMethod.getReturnMessageOK();
    }


//    @PostMapping("/getIntroduceData")
//    @PreAuthorize(" hasRole('ADMIN')")
//    public DataResponse getIntroduceData(@Valid @RequestBody DataRequest dataRequest) {
//        Map data = introduceService.getIntroduceDataMap();
//        return CommonMethod.getReturnData(data);
//    }

}
