package org.springframework.example.interceptor;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final Logger LOGGER = LogManager.getLogger();
    @Getter
    private String body;
    private byte[] cachedBody;
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        request.getParameterNames();

        InputStream inputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(inputStream);

        if(request.getCharacterEncoding() != null){
            this.body = new String(cachedBody, Charset.forName(request.getCharacterEncoding()));
        } else{
            this.body = new String(cachedBody, StandardCharsets.UTF_8);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final InputStream cachedBodyInputStream = new ByteArrayInputStream(this.cachedBody);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                try {
                    return cachedBodyInputStream.available() == 0;
                }catch (IOException ioe){
                    LOGGER.warn("Error :", ioe);
                }
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                //do nothing
            }

            @Override
            public int read() throws IOException {
                return cachedBodyInputStream.read();
            }
        };
    }


    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}
