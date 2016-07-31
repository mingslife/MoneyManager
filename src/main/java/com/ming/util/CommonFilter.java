package com.ming.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ming.dao.BaseDao;

/**
 * 公共过滤器类
 * @author Ming
 * @description 设置请求和响应的字符集和权限控制
 * @date 2015-02-14
 */
public class CommonFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig config;
	private String encoding;
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (this.encoding != null && !"".equals(this.encoding)) {
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding(this.encoding);
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String page = httpServletRequest.getServletPath().replaceAll("'", "''"); // 防止SQL注入！！
		if (page.matches("(.*)html") || page.matches("(.*)htm") || page.matches("(.*)jsp")) {
			List<Map<String, Object>> countPage = BaseDao.load("SELECT COUNT(*) result FROM Page WHERE page = '" + page + "'");
			if (countPage != null && !countPage.get(0).get("result").equals(0L)) {
				String userId = (String) httpServletRequest.getSession().getAttribute("userId");
				if (userId == null) {
					String path = httpServletRequest.getContextPath();
					((HttpServletResponse) response).sendRedirect(path + "/");
					return;
				}
				List<Map<String, Object>> pageList = BaseDao.load("SELECT m.page FROM User u LEFT JOIN Page m ON u.roleId = m.roleId WHERE u.id = '" + userId + "'");
				boolean hasAdmission = false;
				for (int i = 0, length = pageList.size(); i < length; i++) {
					Map<String, Object> pageMap = pageList.get(i);
					if (pageMap.containsValue(page)) {
						hasAdmission = true;
						break;
					}
				}
				if (!hasAdmission) {
					((HttpServletResponse) response).sendError(404);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String jdbcConfigPath = filterConfig.getServletContext().getRealPath("/WEB-INF/classes/jdbc.properties");
		new ConnectionPool(jdbcConfigPath);
		ConsoleMessage.info("公共过滤器准备就绪");
	}
}
