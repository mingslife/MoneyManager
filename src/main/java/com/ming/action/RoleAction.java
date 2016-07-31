package com.ming.action;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ming.entity.Role;
import com.ming.service.IRoleService;
import com.ming.service.RoleService;
import com.ming.util.Permission;
import com.ming.util.WebUtil;
import com.opensymphony.xwork2.ActionSupport;

public class RoleAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	static final Gson GSON = new Gson();
	static final IRoleService roleService = new RoleService();
	
	/* 开始配置数据接收 */
	private int id;
	private String role;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	/* 结束配置数据接收 */

	/**
	 * 读取多条数据
	 */
	@Permission
	public void load() {
		try {
			String[] params = new String[] {"roleId", "roleName"};
			
			List<Map<String, Object>> datas = roleService.load(params, "1=1 ORDER BY roleId DESC");
			if (datas != null) {
				String json = GSON.toJson(datas);
				WebUtil.writeJson(json);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 获取数据条数
	 */
	@Permission
	public void count() {
		try {
			int length = roleService.count();
			if (length != -1) {
				String json = "{\"count\":" + length + "}";
				WebUtil.writeJson(json);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 读取一条数据
	 */
	@Permission
	public void loadOne() {
		try {
			String[] params = new String[] {"roleId", "roleName"};
			
			Map<String, Object> data = roleService.loadOne(params, "roleId = '" + id + "'");
			if (data != null) {
				String json = GSON.toJson(data);
				WebUtil.writeJson(json);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 保存一条数据
	 */
	@Permission
	public void save() {
		try {
			Role roleObject = GSON.fromJson(role, Role.class);
			
			boolean isSucceed = roleService.save(roleObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 修改一条数据
	 */
	@Permission
	public void edit() {
		try {
			Role roleObject = GSON.fromJson(role, Role.class);
			
			boolean isSucceed = roleService.update(roleObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 删除一条数据
	 */
	@Permission
	public void delete() {
		try {
			Role roleObject = new Role(id);
			
			boolean isSucceed = roleService.delete(roleObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
}
