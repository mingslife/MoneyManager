package com.ming.entity;

import com.ming.util.ID;

public class Permission {
	@ID
	private String id;
	private String actionName;
	private Integer roleId;
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Permission() {
	}
	
	public Permission(String id) {
		this.id = id;
	}
	
	public Permission(String id, String actionName, Integer roleId, String remark) {
		this.id = id;
		this.actionName = actionName;
		this.roleId = roleId;
		this.remark = remark;
	}
}
