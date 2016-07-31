package com.ming.entity;

import com.ming.util.ID;

/**
 * 
 * @author Ming
 * @description
 * @date 2015-06-12
 */
public class Menu {
	@ID
	private String menuId;
	private String name;
	private String url;
	private String href;
	private Integer no;
	private Integer roleId;
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Menu() {
	}
	
	public Menu(String menuId) {
		this.menuId = menuId;
	}
	
	public Menu(String menuId, String name, String url, Integer roleId) {
		this.menuId = menuId;
		this.name = name;
		this.url = url;
		this.roleId = roleId;
	}
}
