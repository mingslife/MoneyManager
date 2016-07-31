package com.ming.entity;

import com.ming.util.ID;

/**
 * 
 * @author Ming
 * @description
 * @date 2015-07-10
 */
public class Message {
	public static final String TYPE_SUCCESS = "0"; // alert-success
	public static final String TYPE_INFO = "1"; // alert-info
	public static final String TYPE_WARNING = "2"; // alert-warning
	public static final String TYPE_DANGER = "3"; // alert-danger
	
	@ID
	private String id;
	private String type;
	private String content;
	private Double createTime;
	private String userId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Double createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Message() {
	}
	
	public Message(String id) {
		this.id = id;
	}
	
	public Message(String id, String type, String content, Double createTime, String userId) {
		this.id = id;
		this.type = type;
		this.content = content;
		this.createTime = createTime;
		this.userId = userId;
	}
}
