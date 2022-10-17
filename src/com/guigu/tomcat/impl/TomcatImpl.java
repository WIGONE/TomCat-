package com.guigu.tomcat.impl;

import com.guigu.tomcat.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TomcatImpl implements Tomcat {
    private String webroot = "F:\\Test\\老王的个人博客(6.5)";


    //单列集合 list可重复 set不可重复
    //双列集合 map
    //servlet 容器
    private Map<String, HttpServlet> servletMap = new HashMap<>();

    {//块分为静态块和实例块 块是没有名字的构造函数 块在构造函数之前执行
        servletMap.put("/hello.do",new HelloServlet());
    }

    public static void main(String[] args) {
        new TomcatImpl().start(8080);
    }

    @Override
    public void start(int port) {
        ServerSocket server = null;
        System.out.println("启动服务器：" + port);
        try {
            server = new ServerSocket(port);
            while (true) {
                Socket socket = server.accept();
                //构建请求和响应对象
                HttpServletRequest httpServletRequest = buildRequest(socket);
                HttpServletResponse httpServletResponse = buildResponse(socket, httpServletRequest);
                System.out.println(httpServletRequest);

                if (isServletRequest(httpServletRequest)) {
                    //servlet
                    String uri = getURI(httpServletRequest);
                    HttpServlet servlet = servletMap.get(uri);
                    try {
                        //service 分发请求
                        servlet.service(httpServletRequest, httpServletResponse);
                        //数据推送到服务器
                        httpServletResponse.flushBuffer();

                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                } else if (isStaticRequest(httpServletRequest)) {
                    //处理静态请求
                    processStaticRequest(httpServletRequest, httpServletResponse);
                }else{
                    //404错误
                    //返回404页面
                }

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getURI(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        if (uri.indexOf("?") > 0) {
            uri = uri.substring(0, uri.indexOf("?"));
        }
        return uri;

    }
    @Override
    public boolean isStaticRequest(HttpServletRequest request) {
        String filepath = webroot +request.getRequestURI();
        //截取？前面的路径
        if(filepath.indexOf("?")>0){
            filepath = filepath.substring(0,filepath.indexOf("?"));
        }
//物理文件存在那么是静态请求
        return new File(filepath).exists();
    }
    @Override
    public boolean isServletRequest(HttpServletRequest request) {
        String uri = getURI(request);
        return servletMap.containsKey(uri);
    }
    @Override
    public HttpServletRequest buildRequest(Socket socket) {
        try {

            return new HttpServletRequestImpl(socket);
        } catch (IOException e) {
            //异常转型==》异常链 IO异常属于编译时异常
            throw new RuntimeException(e);

        }

    }

    @Override
    public HttpServletResponse buildResponse(Socket socket, HttpServletRequest request) {
        try {
            return new HttpServletResponseImpl(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





    @Override
    public boolean processStaticRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应输出流
        OutputStream out = response.getOutputStream();

        out.write("HTTP/1.1 200 OK\n".getBytes());
        String contentType = getMimeType(request.getRequestURI());
        out.write(("Content-Type:" + contentType + "\n").getBytes());
        out.write("\n".getBytes());


        String filepath = webroot + request.getRequestURI();
        System.out.println(request.getRequestURI());
        System.out.println(filepath);
        //截取？前面的路径
        if (filepath.indexOf("?") > 0) {
            filepath = filepath.substring(0, filepath.indexOf("?"));
        }
        try (FileInputStream fis = new FileInputStream(filepath)) {
            byte[] bytes = new byte[1024];
            int count;
            while ((count = fis.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
        }
        return true;
    }

    @Override
    public boolean processServletRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public String getMimeType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        } else if (path.endsWith(".js")) {
            return "application/X-javascript";

        } else {
            return "text/html;charset=utf-8";
        }

    }

    @Override
    public HttpServlet getServlet(HttpServletRequest request) {
        return null;
    }

    @Override
    public void createSession(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void stop() {

    }
}
