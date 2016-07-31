package com.ming.entity;

import com.ming.util.ID;

/**
 * 
 * @author Ming
 * @description
 * @date 2015-03-12
 */
public class User {
	public static final Integer ROLEID_SUPER_MANAGER = 0; // 超级管理员
	public static final Integer ROLEID_MANAGER = 1; // 管理员
	public static final Integer ROLEID_NORMAL_USER = 2; // 普通用户
	
	public static final Integer ISACTIVE_NO = 0; // 未激活
	public static final Integer ISACTIVE_YES = 1; // 已激活
	
	@ID
	private String id;
	private String loginName;
	private String userName;
	private String password;
	private String sex;
	private Double birthday;
	private String email;
	private String qq;
	private Integer roleId;
	private Integer isActive;
	private Double createDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getBirthday() {
		return birthday;
	}

	public void setBirthday(Double birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Double getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Double createDate) {
		this.createDate = createDate;
	}
	
	public User() {
	}

	public User(String id) {
		this.id = id;
	}
	
	public User(String id, String loginName, String userName, String password, String sex, Double birthday, String email, String qq, Integer roleId, Integer isActive, Double createDate) {
		this.id = id;
		this.loginName = loginName;
		this.userName = userName;
		this.password = password;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.qq = qq;
		this.roleId = roleId;
		this.isActive = isActive;
		this.createDate = createDate;
	}
}
