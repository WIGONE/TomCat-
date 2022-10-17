package com.guigu.tomcat.impl;

import com.guigu.tomcat.HttpServlet;
import com.guigu.tomcat.HttpServletRequest;
import com.guigu.tomcat.HttpServletResponse;
import com.guigu.tomcat.ServletException;

import java.io.IOException;
/*
抽象类必须重写其中的抽象方法
继承了抽象类的子类需要把抽象类中的方法重写一遍吗？
抽象类中的非抽象方法不用重写，其他必须重写，接口的方法必须重写；
接口和抽象类中只有方法名，没有定义的，如果你不定义 也就是空方法，
接口就是为了弥补java不能多重继承，接口针对的是对象而不是实现。
实现的部分可以交由对象去实现。这就是java中的多态啊。
 */
public abstract class HttpServletImpl implements HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //分发请求
        if("GET".equals(request.getMethod())){
            doGet(request,response);
        } else if ("POST".equals(request.getMethod())) {
            doPost(request,response);
        }else if ("OPTIONS".equals(request.getMethod())) {
            doOptions(request,response);
        }else if ("PUT".equals(request.getMethod())) {
            doPut(request,response);
        }else if ("HEAD".equals(request.getMethod())) {
            doHead(request,response);
        }else if ("TRACE".equals(request.getMethod())) {
            doTrace(request,response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
throw new ServletException("405,错误!");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405,错误!");

    }

    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405,错误!");

    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405,错误!");

    }

    @Override
    public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405,错误!");

    }

    @Override
    public void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405,错误!");

    }

//    @Override
   abstract public void init() ;

//    @Override
  abstract  public void destroy() ;
}
