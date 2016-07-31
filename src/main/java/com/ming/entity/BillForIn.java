package com.ming.entity;

import com.ming.util.ID;

/**
 * 
 * @author Ming
 * @description
 * @date 2015-03-12
 */
public class BillForIn {
	@ID
	private String id;
	private Double date;
	private Double amount;
	private String fromId;
	private String toId;
	private String recordId;
	private Double createDate;
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getDate() {
		return date;
	}

	public void setDate(Double date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public Double getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Double createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BillForIn() {
	}
	
	public BillForIn(String id) {
		this.id = id;
	}
	
	public BillForIn(String id, Double date, Double amount, String fromId, String toId, String recordId, Double createDate, String remark) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.fromId = fromId;
		this.toId = toId;
		this.recordId = recordId;
		this.createDate = createDate;
		this.remark = remark;
	}
}
