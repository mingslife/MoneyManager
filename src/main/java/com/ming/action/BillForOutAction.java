package com.ming.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ming.entity.BillForOut;
import com.ming.entity.Message;
import com.ming.entity.User;
import com.ming.service.BillForInService;
import com.ming.service.BillForOutService;
import com.ming.service.IBillForInService;
import com.ming.service.IBillForOutService;
import com.ming.service.IMessageService;
import com.ming.service.IUserService;
import com.ming.service.MessageService;
import com.ming.service.UserService;
import com.ming.util.Condition;
import com.ming.util.DataUtil;
import com.ming.util.KaptchaUtil;
import com.ming.util.MailUtil;
import com.ming.util.Permission;
import com.ming.util.WebUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BillForOutAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static final Gson GSON = new Gson();
	private static final IBillForOutService billForOutService = new BillForOutService();
	private static final IMessageService messageService = new MessageService();
	private static final IUserService userService = new UserService();
	private static final IBillForInService billForInService = new BillForInService();
	
	/* 开始配置数据接收 */
	private String id;
	private String billForOut;
	private int curPage;
	private int limit;
	private String condition;
	private String validCode;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillForOut() {
		return billForOut;
	}

	public void setBillForOut(String billForOut) {
		this.billForOut = billForOut;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	/* 结束配置数据接收 */

	/**
	 * 读取多条数据
	 */
	@Permission
	public void load() {
		try {
			List<Map<String, Object>> datas = billForOutService.loadSpecial(conditionString(), curPage, limit);
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
			int length = billForOutService.countSpecial(conditionString());
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
			String[] params = new String[] {"id", "date", "event", "amount", "payId", "status", "recordId", "createDate", "remark"};
			
			Map<String, Object> data = billForOutService.loadOne(params, "id = '" + id + "'");
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
			BillForOut billForOutObject = GSON.fromJson(billForOut, BillForOut.class);
			billForOutObject.setId(DataUtil.getUUID());
			billForOutObject.setCreateDate(Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMdd")));
			
			boolean isSucceed = billForOutService.save(billForOutObject);
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
			BillForOut billForOutObject = GSON.fromJson(billForOut, BillForOut.class);
			
			boolean isSucceed = billForOutService.update(billForOutObject);
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
			BillForOut billForOutObject = new BillForOut(id);
			
			boolean isSucceed = billForOutService.delete(billForOutObject);
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
	 * 组合条件
	 * @return 条件SQL语句
	 */
	private String conditionString() {
		try {
			if (condition != null) {
				Condition conditionObject = GSON.fromJson(condition, Condition.class);
				
				String conditionString = "";
				if (conditionObject.getPayName() != null)
					conditionString += "payName LIKE '%" + conditionObject.getPayName() + "%' AND ";
				if (conditionObject.getStatus() != null)
					conditionString += "status = '" + conditionObject.getStatus() + "' AND ";
				
				if (conditionString.length() > 5)
					conditionString = conditionString.substring(0, conditionString.length() - 5);
				
				return conditionString;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取个人报账数据
	 */
	@Permission
	public void loadMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			List<Map<String, Object>> datas = billForOutService.loadMineSpecial(userId, conditionMineString(), curPage, limit);
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
	 * 获取个人报账数据条数
	 */
	@Permission
	public void countMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			int length = billForOutService.countMineSpecial(userId, conditionMineString());
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
	 * 个人报账数据组合条件
	 * @return 条件SQL语句
	 */
	private String conditionMineString() {
		try {
			if (condition != null) {
				Condition conditionObject = GSON.fromJson(condition, Condition.class);
				
				String conditionString = "";
				if (conditionObject.getStatus() != null)
					conditionString += "status = '" + conditionObject.getStatus() + "' AND ";
				
				if (conditionString.length() > 5)
					conditionString = conditionString.substring(0, conditionString.length() - 5);
				
				return conditionString;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取多条报账数据
	 */
	@Permission
	public void loadReview() {
		try {
			List<Map<String, Object>> datas = billForOutService.loadReviewSpecial(conditionReviewString(), curPage, limit);
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
	 * 获取报账数据条数
	 */
	@Permission
	public void countReview() {
		try {
			int length = billForOutService.countReviewSpecial(conditionReviewString());
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
	 * 读取一条报账数据
	 */
	@Permission
	public void loadOneReview() {
		try {
			Map<String, Object> data = billForOutService.loadOneReviewSpecial("id = '" + id + "'");
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
	 * 组合报账条件
	 * @return 条件SQL语句
	 */
	private String conditionReviewString() {
		try {
			if (condition != null) {
				Condition conditionObject = GSON.fromJson(condition, Condition.class);
				
				String conditionString = "";
				if (conditionObject.getPayName() != null)
					conditionString += "payName LIKE '%" + conditionObject.getPayName() + "%' AND ";
				
				if (conditionString.length() > 5)
					conditionString = conditionString.substring(0, conditionString.length() - 5);
				
				return conditionString;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 审核通过
	 */
	@Permission
	public void reviewOk() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String userName = (String) session.get("userName");
			
			BillForOut billForOutObject = GSON.fromJson(billForOut, BillForOut.class);
			billForOutObject.setPayId(null);
			billForOutObject.setRecordId(userId);
			billForOutObject.setCreateDate(null);
			
			if (billForOutObject.getStatus() == BillForOut.STATUS_PAID) {
				double amount = billForOutObject.getAmount();
				double money = billForInService.sumUpMoney();
				if (amount > money) {
					WebUtil.writeJson("{\"result\":\"记录更新失败，财务余额不足\"}");
					return;
				}
			}
			
			boolean isSucceed = billForOutService.update(billForOutObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
			
			String[] params = new String[] {"payId", "createDate"};
			Map<String, Object> data = billForOutService.loadOne(params, "id = '" + billForOutObject.getId() + "'");
			String payId = (String) data.get("payId");
			Double createDate = (Double) data.get("createDate");
			
			/* 生成消息 */
			String messageContent = "您在" + DataUtil.dateFormat(createDate) + "提交的报账申请经" + userName + "审核已通过";
			double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
			Map<String, Object> payUserData = userService.loadOne(new String[] {"userName", "email"}, "id = '" + payId + "'");
			String payUserUserName = (String) payUserData.get("userName");
			String payUserEmail = (String) payUserData.get("email");
			
			/* 向支付人发送一条消息 */
			Message messageObject = new Message();
			messageObject.setId(DataUtil.getUUID());
			messageObject.setType(Message.TYPE_INFO);
			messageObject.setContent(messageContent);
			messageObject.setCreateTime(messageDate);
			messageObject.setUserId(payId);
			messageService.save(messageObject);
			
			/* 向支付人发送一条邮件 */
			MailUtil.sendInBackground(payUserEmail, "财务管理系统", "<p>尊敬的" + payUserUserName + "：</p><p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 审核不通过
	 */
	@Permission
	public void reviewNo() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String userName = (String) session.get("userName");
			
			BillForOut billForOutObject = GSON.fromJson(billForOut, BillForOut.class);
			billForOutObject.setPayId(null);
			billForOutObject.setRecordId(userId);
			billForOutObject.setCreateDate(null);
			billForOutObject.setStatus(BillForOut.STATUS_FAILED);
			
			boolean isSucceed = billForOutService.update(billForOutObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
			
			String[] params = new String[] {"payId", "createDate"};
			Map<String, Object> data = billForOutService.loadOne(params, "id = '" + billForOutObject.getId() + "'");
			String payId = (String) data.get("payId");
			Double createDate = (Double) data.get("createDate");
			
			/* 生成消息 */
			String messageContent = "您在" + DataUtil.dateFormat(createDate) + "提交的报账申请经" + userName + "审核不通过";
			double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
			Map<String, Object> payUserData = userService.loadOne(new String[] {"userName", "email"}, "id = '" + payId + "'");
			String payUserUserName = (String) payUserData.get("userName");
			String payUserEmail = (String) payUserData.get("email");
			
			/* 向支付人发送一条消息 */
			Message messageObject = new Message();
			messageObject.setId(DataUtil.getUUID());
			messageObject.setType(Message.TYPE_DANGER);
			messageObject.setContent(messageContent);
			messageObject.setCreateTime(messageDate);
			messageObject.setUserId(payId);
			messageService.save(messageObject);
			
			/* 向支付人发送一条邮件 */
			MailUtil.sendInBackground(payUserEmail, "财务管理系统", "<p>尊敬的" + payUserUserName + "：</p><p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 个人报账
	 */
	@Permission
	public void apply() {
		try {
			if (!KaptchaUtil.checkCode(validCode)) {
				WebUtil.writeJson(WebUtil.JSON_WRONG_VALIDCODE);
				return;
			}
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String userName = (String) session.get("userName");
			
			BillForOut billForOutObject = GSON.fromJson(billForOut, BillForOut.class);
			billForOutObject.setId(DataUtil.getUUID());
			billForOutObject.setPayId(userId);
			billForOutObject.setStatus(2); // 2表示待审核
			billForOutObject.setRecordId(null);
			billForOutObject.setCreateDate(Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMdd")));
			
			boolean isSucceed = billForOutService.save(billForOutObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
			
			String[] params = new String[] {"id", "userName", "email"};
			List<Map<String, Object>> datas = userService.load(params, "roleId = '" + User.ROLEID_SUPER_MANAGER + "'");
			
			/* 生成消息 */
			String messageContent = userName + "报账，等待您的审核";
			double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
			
			/* 向所有超级管理员发送一条消息 */
			Message messageObject = new Message();
			messageObject.setId(DataUtil.getUUID());
			messageObject.setType(Message.TYPE_WARNING);
			messageObject.setContent(messageContent);
			messageObject.setCreateTime(messageDate);
			for (int i = 0, length = datas.size(); i < length; i++) {
				String currentUserId = (String) datas.get(i).get("id");
				String currentUserName = (String) datas.get(i).get("userName");
				String currentUserEmail = (String) datas.get(i).get("email");
				messageObject.setUserId(currentUserId);
				messageService.save(messageObject);
				
				/* 向所有超级管理员发送一条邮件 */
				MailUtil.sendInBackground(currentUserEmail, "财务管理系统", "<p>尊敬的" + currentUserName + "：</p><p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 读取个人报账数据
	 */
	@Permission
	public void loadPay() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			List<Map<String, Object>> datas = billForOutService.loadPaySpecial(userId, conditionPayString(), curPage, limit);
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
	 * 获取个人报账数据条数
	 */
	@Permission
	public void countPay() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			int length = billForOutService.countPaySpecial(userId, conditionPayString());
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
	 * 个人报账数据组合条件
	 * @return 条件SQL语句
	 */
	private String conditionPayString() {
		try {
			if (condition != null) {
				Condition conditionObject = GSON.fromJson(condition, Condition.class);
				
				String conditionString = "";
				if (conditionObject.getPayName() != null)
					conditionString += "payName LIKE '%" + conditionObject.getPayName() + "%' AND ";
				if (conditionObject.getStatus() != null)
					conditionString += "status = '" + conditionObject.getStatus() + "' AND ";
				
				if (conditionString.length() > 5)
					conditionString = conditionString.substring(0, conditionString.length() - 5);
				
				return conditionString;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 更改状态为已支付
	 */
	@Permission
	public void paid() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String userName = (String) session.get("userName");
			
			String[] params = new String[] {"amount", "payId", "createDate"};
			Map<String, Object> data = billForOutService.loadOne(params, "id = '" + id + "' AND recordId = '" + userId + "'");
			if (data != null) {
				double amount = (Double) data.get("amount");
				double money = billForInService.sumUpMoney();
				if (amount > money) {
					WebUtil.writeJson("{\"result\":\"记录更新失败，财务余额不足\"}");
					return;
				}
				
				BillForOut billForOutObject = new BillForOut(id);
				billForOutObject.setStatus(BillForOut.STATUS_PAID);
				
				boolean isSucceed = billForOutService.update(billForOutObject);
				if (isSucceed) {
					WebUtil.writeJson(WebUtil.JSON_SUCCESS);
				} else {
					WebUtil.writeJson(WebUtil.JSON_ERROR);
				}
				
				String payId = (String) data.get("payId");
				Double createDate = (Double) data.get("createDate");
				
				/* 生成消息 */
				String messageContent = "您在" + DataUtil.dateFormat(createDate) + "提交的报账记录被" + userName + "标记为已支付";
				double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
				Map<String, Object> payUserData = userService.loadOne(new String[] {"userName", "email"}, "id = '" + payId + "'");
				String payUserUserName = (String) payUserData.get("userName");
				String payUserEmail = (String) payUserData.get("email");
				
				/* 向支付人发送一条消息 */
				Message messageObject = new Message();
				messageObject.setId(DataUtil.getUUID());
				messageObject.setType(Message.TYPE_SUCCESS);
				messageObject.setContent(messageContent);
				messageObject.setCreateTime(messageDate);
				messageObject.setUserId(payId);
				messageService.save(messageObject);
				
				/* 向支付人发送一条邮件 */
				MailUtil.sendInBackground(payUserEmail, "财务管理系统", "<p>尊敬的" + payUserUserName + "：</p><p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
			} else {
				WebUtil.writeJson("{\"result\":\"非法注入\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 更改状态为未支付
	 */
	@Permission
	public void unpaid() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String userName = (String) session.get("userName");
			
			Map<String, Object> data = billForOutService.loadOne("id = '" + id + "' AND recordId = '" + userId + "'");
			if (data != null) {
				BillForOut billForOutObject = new BillForOut(id);
				billForOutObject.setStatus(BillForOut.STATUS_UNPAID);
				
				boolean isSucceed = billForOutService.update(billForOutObject);
				if (isSucceed) {
					WebUtil.writeJson(WebUtil.JSON_SUCCESS);
				} else {
					WebUtil.writeJson(WebUtil.JSON_ERROR);
				}
				
				String payId = (String) data.get("payId");
				Double createDate = (Double) data.get("createDate");
				
				/* 生成消息 */
				String messageContent = "您在" + DataUtil.dateFormat(createDate) + "提交的报账记录被" + userName + "标记为未支付";
				double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
				Map<String, Object> payUserData = userService.loadOne(new String[] {"userName", "email"}, "id = '" + payId + "'");
				String payUserUserName = (String) payUserData.get("userName");
				String payUserEmail = (String) payUserData.get("email");
				
				/* 向支付人发送一条消息 */
				Message messageObject = new Message();
				messageObject.setId(DataUtil.getUUID());
				messageObject.setType(Message.TYPE_WARNING);
				messageObject.setContent(messageContent);
				messageObject.setCreateTime(messageDate);
				messageObject.setUserId(payId);
				messageService.save(messageObject);
				
				/* 向支付人发送一条邮件 */
				MailUtil.sendInBackground(payUserEmail, "财务管理系统", "<p>尊敬的" + payUserUserName + "：</p><p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
			} else {
				WebUtil.writeJson("{\"result\":\"非法注入\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 支出金额统计
	 */
	@Permission
	public void sumUp() {
		try {
			double sum = billForOutService.sumUp(conditionString());
			String json = "{\"amount\":" + sum + "}";
			WebUtil.writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 个人报账金额统计
	 */
	@Permission
	public void sumUpMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			double sum = billForOutService.sumUpMine(userId, conditionMineString());
			String json = "{\"amount\":" + sum + "}";
			WebUtil.writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 报账金额统计
	 */
	@Permission
	public void sumUpReview() {
		try {
			double sum = billForOutService.sumUpReview(conditionMineString());
			String json = "{\"amount\":" + sum + "}";
			WebUtil.writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 支付金额统计
	 */
	@Permission
	public void sumUpPay() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			double sum = billForOutService.sumUpPay(userId, conditionMineString());
			String json = "{\"amount\":" + sum + "}";
			WebUtil.writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 支付管理查看还需金额
	 */
	@Permission
	public void sumUpNeed() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			double leftMoney = billForInService.sumUpMoney();
			double unpaidMoney = billForOutService.sumUpPay(userId, "status = " + BillForOut.STATUS_UNPAID);
			if (unpaidMoney > leftMoney)
				WebUtil.writeJson("{\"amount\":" + (unpaidMoney - leftMoney) + "}");
			else
				WebUtil.writeJson("{\"amount\":null}");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
}
