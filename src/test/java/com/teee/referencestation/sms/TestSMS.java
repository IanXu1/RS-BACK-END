package com.teee.referencestation.sms;

import com.teee.referencestation.ReferenceStationApplication;
import com.teee.referencestation.common.base.service.ShortMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReferenceStationApplication.class)
public class TestSMS {

    @Autowired
    private ShortMessageService smsService;
    @Autowired
    protected SqlSessionTemplate session;

    @Test
    public void testSendSMS() {
        smsService.sendMsgUtf8("测试内容", "15928512657,18010537783");
    }

    @Test
    public void testGetConfigUsers() {
        List<Long> userIdList = session.selectList("warningUserMapping.selectMappingByConfigId", 1l);
        System.out.println(userIdList);
    }
}
