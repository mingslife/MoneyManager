package com.ming.util;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Kaptcha验证码工具类
 * @author Ming
 * @description 获取生成的验证码并用于验证，基于Struts2
 * @date 2015-06-30
 */
public class KaptchaUtil extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * 获取验证码
	 * @return 验证码
	 */
	public static String getCode() {
		try {
			String code = (String) ActionContext.getContext().getSession().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void removeCode() {
		try {
			ActionContext.getContext().getSession().remove(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkCode(String validCode) {
		try {
			String code = getCode();
			if (code != null && validCode != null && code.equalsIgnoreCase(validCode)) {
				removeCode();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
