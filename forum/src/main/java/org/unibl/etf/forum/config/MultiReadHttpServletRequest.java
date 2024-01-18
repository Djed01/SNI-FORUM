package org.unibl.etf.forum.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.*;
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBody;

    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        cacheRequestBody(request);
    }

    private void cacheRequestBody(HttpServletRequest request) throws IOException {
        // Read the body and cache it
        InputStream inputStream = request.getInputStream();
        this.cachedBody = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            this.cachedBody.write(buffer, 0, bytesRead);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody.toByteArray());
    }

    private class CachedBodyServletInputStream extends ServletInputStream {
        private ByteArrayInputStream inputStream;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.inputStream = new ByteArrayInputStream(cachedBody);
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
        public void setReadListener(ReadListener readListener) {
            throw new RuntimeException("Not implemented");
        }
    }
}
