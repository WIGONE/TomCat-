package com.guigu.tomcat.impl;

import com.guigu.tomcat.Cookie;
import com.guigu.tomcat.HttpServletResponse;

import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpServletResponseImpl implements HttpServletResponse {

    private Socket socket;
    private OutputStream out;
    //字符输出流==》 内存 ==》资源流
    private CharArrayWriter charArrayWriter = new CharArrayWriter();
    //PrintWriter打印输出流 定义输出的格式 输出到哪里 该流不知道 ==》 处理流
    private PrintWriter printWriter = new PrintWriter(charArrayWriter);
    //LinkedHashMap==>有序的map集合
    private Map<String, String> headerMap = new LinkedHashMap<>();
    private int status = 200;
    private String msg = "OK";

    public HttpServletResponseImpl(Socket socket) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public void setContentType(String type) {
        this.setHeader("Content-Type", type);
    }

    @Override
    public void setStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;

    }

    @Override
    public void setHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public void flushBuffer() {
        try {
            //头行
            out.write(("HTTP/1.1" + status + " " + msg + "\n").getBytes());
            //头域
            headerMap.forEach((key, value) -> {
                try {
                    out.write((key + ":" + value + "\n").getBytes());
                } catch(IOException e){
                  e.printStackTrace();
                }
            });
           //CRLF空行
            out.write(("\n").getBytes());
            //body正文
            String content = charArrayWriter.toString();
            out.write(content.getBytes());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendRedirect(String location) {

    }

    @Override
    public void addCookie(Cookie cookie) {

    }
}
