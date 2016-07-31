package com.ming.entity;

import com.ming.util.ID;

/**
 * 
 * @author Ming
 * @description
 * @date 2015-03-12
 */
public class Page {
	@ID
	private String pageId;
	private String page;
	private Integer roleId;
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Page() {
	}

	public Page(String pageId) {
		this.pageId = pageId;
	}
	
	public Page(String pageId, String page, Integer roleId) {
		this.pageId = pageId;
		this.page = page;
		this.roleId = roleId;
	}
}
