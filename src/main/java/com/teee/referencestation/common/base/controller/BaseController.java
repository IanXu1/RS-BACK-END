package com.teee.referencestation.common.base.controller;


import com.teee.referencestation.api.upgrade.model.UploadFile;
import com.teee.referencestation.utils.BeanUtil;
import com.teee.referencestation.utils.HttpServletUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Controller基类，封装一些通用的方法，如获取入参，加解密等等
 *
 * @author LDB
 * @date 2018/11/09 17:47
 */
public class BaseController {


    /**
     * 将获取的参数转换为指定bean
     *
     * @param request HttpServletRequest
     * @param cls     Class
     * @param <T>     T
     * @return T
     */
    public <T> T getParameter(HttpServletRequest request, Class<T> cls) {
        return BeanUtil.toBean(HttpServletUtil.getRequestParameters(request), cls);
    }
}
