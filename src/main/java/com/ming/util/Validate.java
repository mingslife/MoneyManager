package com.ming.util;


/**
 * 验证类
 * @author Ming
 * @description 验证数据合法性，未完工
 * @date 2015-02-21
 */
public class Validate {
	/**
	 * 验证字符串
	 * @param value 待验证的数据
	 * @return　结果
	 */
	public static String string(String value) {
		if (value != null)
			return value.trim();
		else
			return null;
	}
	
	/**
	 * 验证字符串（带长度验证）
	 * @param value 待验证的数据
	 * @param length 长度
	 * @return 结果
	 */
	public static String string(String value, int length) {
		value = string(value);
		if (value != null && value.length() <= length)
			return value;
		else
			return null;
	}
	
	// TODO 未完待续
}
