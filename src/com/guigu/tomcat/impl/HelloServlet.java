package com.guigu.tomcat.impl;

import com.guigu.tomcat.HttpServletRequest;
import com.guigu.tomcat.HttpServletResponse;
import com.guigu.tomcat.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends  HttpServletImpl {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        //相应对象会临时保存append的内容
        writer.append("<h2>Hello,World!</h2>");
        //作业 获取参数并输出
        //http
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
