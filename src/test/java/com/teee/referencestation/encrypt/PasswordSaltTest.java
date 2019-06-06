package com.teee.referencestation.encrypt;

import com.teee.referencestation.ReferenceStationApplication;
import com.teee.referencestation.api.user.model.UserAddVo;
import com.teee.referencestation.api.user.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date: 22019-1-22 18:40:26
 * @description: 给 密码进行 加密加盐  盐值默认为 用户名
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReferenceStationApplication.class)
public class PasswordSaltTest {

    @Test
    public void test() throws Exception {
        System.out.println(md5("123456","78d92ba9477b3661bc8be4bd2e8dd8c0"));
    }

    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }
}
