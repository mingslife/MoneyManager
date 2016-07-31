package com.ming.entity;

import com.ming.util.ID;

/**
 * 
 * @author Ming
 * @description
 * @date 2015-03-12
 */
public class BillForOut {
	public static final Integer STATUS_UNPAID = 0; // 未支付
	public static final Integer STATUS_PAID = 1; // 已支付
	public static final Integer STATUS_UNCHECK = 2; // 未审核
	public static final Integer STATUS_FAILED = 3; // 不通过
	
	@ID
	private String id;
	private Double date;
	private String event;
	private Double amount;
	private String payId;
	private Integer status;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public BillForOut() {
	}
	
	public BillForOut(String id) {
		this.id = id;
	}
	
	public BillForOut(String id, Double date, String event, Double amount, String payId, Integer status, String recordId, Double createDate, String remark) {
		this.id = id;
		this.date = date;
		this.event = event;
		this.amount = amount;
		this.payId = payId;
		this.status = status;
		this.recordId = recordId;
		this.createDate = createDate;
		this.remark = remark;
	}
}
