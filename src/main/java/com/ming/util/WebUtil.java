package com.ming.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * WebUtil类
 * @author Ming
 * @description 基础Struts2的WebUtil，用于向客户端发送字符串
 * @date 2015-03-13
 */
public class WebUtil extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	public static final String JSON_SUCCESS = "{\"result\":\"success\"}";
	public static final String JSON_ERROR = "{\"result\":\"服务器错误\"}";
	public static final String JSON_LOGIN = "{\"result\":\"用户未登录或会话已过期\"}";
	public static final String JSON_NO_PERMISSION = "{\"result\":\"没有足够的权限\"}";
	public static final String JSON_WRONG_VALIDCODE = "{\"result\":\"验证码错误\"}";
	
	/**
	 * 向前台返回数据
	 * @param string 数据
	 */
	public static void write(String string) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(string);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向前台返回文本
	 * @param string 数据
	 */
	public static void writeText(String string) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(string);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向前台返回XML
	 * @param string 数据
	 */
	public static void writeXml(String string) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(string);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向前台返回JSON
	 * @param string 数据
	 */
	public static void writeJson(String string) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(string);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向前台返回JSONP
	 * @param string 数据
	 */
	public static void writeJsonp(String string) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/javascript; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("handleResponse(" + string + ")");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 控制前台跳转页面
	 * @param url URL
	 */
	public static void sendRedirect(String url) {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			String path = request.getContextPath();
			response.sendRedirect(path + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取request对象
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setCharacterEncoding("UTF-8");
			return request;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取response对象
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取session对象
	 * @return
	 */
	public HttpSession getSession() {
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			return session;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
