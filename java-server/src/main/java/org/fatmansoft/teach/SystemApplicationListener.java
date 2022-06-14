package org.fatmansoft.teach;

import org.fatmansoft.teach.service.base.CommonService;
import org.fatmansoft.teach.service.base.SystemService;
import org.fatmansoft.teach.service.test.TestServ;
import org.fatmansoft.teach.service.test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class SystemApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootSecurityJwtApplication.class);
    @Autowired
    private SystemService systemService;
    @Autowired
    private TestService testService;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("SystemInitStart");
        systemService.loadUimsFile();
        CommonService.setup();//初始化commonService完成实体类扫描和建图
        testService.testMain();
        logger.info("systemInitEnd");
    }

}