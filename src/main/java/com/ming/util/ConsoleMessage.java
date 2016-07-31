package com.ming.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 控制台消息类
 * @author Ming
 * @description 格式化输出控制台消息
 * @date 2015-02-15
 */
public class ConsoleMessage {
	private static String getCurrentDate() {
		TimeZone timeZone = TimeZone.getDefault(); // 获取系统当前市区
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		dateFormat.setTimeZone(timeZone);
		Date date = new Date();
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
	public static void info(String message) {
		System.out.println("[INFO] " + getCurrentDate() + " " + message);
	}
	
	public static void warn(String message) {
		System.out.println("[WARN] " + getCurrentDate() + " " + message);
	}
	
	public static void error(String message) {
		System.out.println("[RROR] " + getCurrentDate() + " " + message);
	}
}
