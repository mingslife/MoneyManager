package com.ming.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * 数据工具类
 * @author Ming
 * @description 提供辅助数据
 * @date 2015-03-10
 */
public class DataUtil {
	/**
	 * 生成32位的UUID
	 * @return UUID字符串
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
//		String uuidString = uuid.toString().replaceAll("-", "");
		String uuidString = uuid.toString();
		return uuidString;
	}
	
	/**
	 * 生成格式化的日期时间
	 * @param date 日期时间对象
	 * @param format 日期时间格式
	 * @return 日期时间字符串
	 */
	public static String getDateString(final Date date, final String format) {
		TimeZone timeZone = TimeZone.getDefault(); // 获取系统当前市区
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
	/**
	 * 将double类型并且形如“yyyyMMdd”的数据转成“yyyy/MM/dd”的字符串
	 * @param data 日期
	 * @return 格式化后的字符串
	 */
	public static String dateFormat(final Double data) {
		try {
			DecimalFormat format = new DecimalFormat("#");
			String dataString = format.format(data);
			return dataString.substring(0, 4) + "/" + dataString.substring(4, 6) + "/" + dataString.substring(6);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 将double类型并且形如“yyyyMMddHHmmss”的数据转成“yyyy/MM/dd HH:mm:ss”的字符串
	 * @param data 日期
	 * @return 格式化后的字符串
	 */
	public static String dateTimeFormat(final Double data) {
		try {
			DecimalFormat format = new DecimalFormat("#");
			String dataString = format.format(data);
			return dataString.substring(0, 4) + "/" + dataString.substring(4, 6) + "/" + dataString.substring(6, 8) + " " + dataString.substring(8, 10) + ":" + dataString.substring(10, 12) + ":" + dataString.substring(12);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
