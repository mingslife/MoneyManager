package com.ming.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.ming.dao.BaseDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 利用注解进行登录方法拦截
 * @author Ming
 * @description 拦截需要登录才能调用的方法
 * @date 2015-03-15
 */
public class PermissionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionProxy actionProxy = invocation.getProxy();
		String currentMethodName = actionProxy.getMethod();
		String actionName = actionProxy.getActionName();
		Class<?> actionClass = invocation.getAction().getClass();
		Method currentMethod = actionClass.getMethod(currentMethodName);
		if (currentMethod.getAnnotation(Permission.class) != null) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			if (session.get("userId") == null) {
				ConsoleMessage.warn("拦截未登录调用" + actionName);
				if (currentMethod.getReturnType().equals(String.class)) {
					return "login";
				} else {
					WebUtil.writeJson(WebUtil.JSON_LOGIN);
					return null;
				}
			}
			Integer roleId = (Integer) session.get("roleId");
			List<Map<String, Object>> data = BaseDao.load("SELECT id FROM Permission WHERE actionName = '" + actionName + "' AND roleId = '" + roleId + "'");
			if (data.size() == 0) {
				ConsoleMessage.warn("拦截无权限调用" + actionName);
				if (currentMethod.getReturnType().equals(String.class)) {
					return "nopower";
				} else {
					WebUtil.writeJson(WebUtil.JSON_NO_PERMISSION);
					return null;
				}
			}
		}
		return invocation.invoke();
	}
}
