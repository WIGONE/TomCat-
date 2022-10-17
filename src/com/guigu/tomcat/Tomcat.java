package com.guigu.tomcat;

import java.io.IOException;
import java.net.Socket;

public interface Tomcat {

	/**
	 * 启动服务器
	 */
	void start(int port) throws IOException;

	/**
	 * 构建请求对象
	 *
	 * @param socket
	 * @return
	 */
	HttpServletRequest buildRequest(Socket socket);

	/**
	 * 构建响应对象
	 * @param socket
	 * @return
	 */
	HttpServletResponse buildResponse(Socket socket, HttpServletRequest request);

	/**
	 * 判断是否是静态请求
	 * @param request
	 * @param response
	 * @return
	 */
	boolean isStaticRequest(HttpServletRequest request);

	/**
	 * 判断是否是动态(Servlet)请求
	 * @param request
	 * @param response
	 * @return
	 */
	boolean isServletRequest(HttpServletRequest request);

	/**
	 * 处理静态请求
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	boolean processStaticRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 处理动态(Servlet)请求
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	boolean processServletRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;
			
	/**
	 * 根据文件名返回ContentType
	 */
	String getMimeType(String path);

	/**
	 * 获取Servlet实例
	 * @param request
	 * @return
	 */
	HttpServlet getServlet(HttpServletRequest request);

	/**
	 * 创建会话对象
	 * @param request
	 * @return
	 */
	void createSession(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 停止服务
	 */
	void stop();

}
