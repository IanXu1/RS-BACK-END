package com.teee.referencestation.config.filter;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zhanglei
 * @desc 使用wrapper增强功能，解决HttpServletRequest#getInputStream只能调用一次的问题
 * @date 2019-3-26 14:12:51
 */
public class RepeatableReadServletRequest extends HttpServletRequestWrapper {

    /**
     * 保存流中的数据
     */
    private byte[] data;

    public RepeatableReadServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        data = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new RepeatableReadInputStream(data), getCharacterEncoding()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new RepeatableReadInputStream(data);
    }


    /**
     * @desc 自定义ServletInputStream
     */
    private static class RepeatableReadInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RepeatableReadInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }
    }
}
