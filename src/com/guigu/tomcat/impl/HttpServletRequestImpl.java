package com.guigu.tomcat.impl;

import com.guigu.tomcat.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpServletRequestImpl implements HttpServletRequest {

    private final InputStream in;
    private  Socket socket;
    private final String method;
    private final String requestURI;
    private final String protocol;

    private Map<String, String> headerMap = new LinkedHashMap<>();

//    private Socket socket;

    @Override
    public String toString() {
        return method;
    }

    public HttpServletRequestImpl(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        String line = readLine(in);
        //解析头行
        String[] items = line.split(" ");
        this.method = items[0];
        this.requestURI = items[1];
        this.protocol = items[2];
        //一直读取直到遇到空行
        //赋值语句外加括号来获取返回值
        while ((line = readLine(in)).isEmpty() == false) {
            items = line.split(":");
            headerMap.put(items[0], items[1]);

        }
        //TODO body(正文暂不处理)
    }

    private String readLine(InputStream in) throws IOException {
        StringBuilder line = new StringBuilder();
        char c;
        while (true) {
            c = (char) in.read();//一次读入一个字符
            //window系统的换行是\r回车\n换行
            if (c == '\r') {
                in.read();//读到\n
                System.out.println(line);
                return line.toString();

            } else {
                line.append(c);
            }

        }

    }


    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getHeader(String name) {
        return headerMap.get(name);
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public void getSession(HttpSession session) {

    }
}
