package com.teee.referencestation.rpc;

import com.teee.referencestation.ReferenceStationApplication;
import com.teee.referencestation.api.upgrade.model.UpgradeVersion;
import com.teee.referencestation.api.upgrade.service.UpgradeVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReferenceStationApplication.class)
public class TestPushUpgrade {

    @Autowired
    private UpgradeVersionService versionService;

    @Test
    public void testPush() {
        int resultCode = versionService.forward2terminal(25);
        System.out.println(resultCode);
    }
}
