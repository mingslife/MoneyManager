package com.ming.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ming.entity.Message;
import com.ming.entity.User;
import com.ming.service.IMessageService;
import com.ming.service.IUserService;
import com.ming.service.MessageService;
import com.ming.service.UserService;
import com.ming.util.DataUtil;
import com.ming.util.HtmlUtil;
import com.ming.util.KaptchaUtil;
import com.ming.util.MailUtil;
import com.ming.util.Permission;
import com.ming.util.WebUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MessageAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static final Gson GSON = new Gson();
	private static final IMessageService messageService = new MessageService();
	private static final IUserService userService = new UserService();
	
	/* 开始配置数据接收 */
	private String id;
	private String message;
	private int curPage;
	private int limit;
	private String condition;
	private String content;
	private String validCode;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
			String[] params = new String[] {"id", "type", "content", "createTime", "userId"};
			
			List<Map<String, Object>> datas = messageService.load(params, conditionString(), curPage, limit);
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
			int length = messageService.count(conditionString());
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
			String[] params = new String[] {"type", "content", "createTime", "userId"};
			
			Map<String, Object> data = messageService.loadOne(params, "id = '" + id + "'");
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
			Message messageObject = GSON.fromJson(message, Message.class);
			messageObject.setId(DataUtil.getUUID());
			messageObject.setCreateTime(Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss")));
			
			boolean isSucceed = messageService.save(messageObject);
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
			Message messageObject = GSON.fromJson(message, Message.class);
			
			boolean isSucceed = messageService.update(messageObject);
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
			Message messageObject = new Message(id);
			
			boolean isSucceed = messageService.delete(messageObject);
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
//				Condition conditionObject = GSON.fromJson(condition, Condition.class);
				
				String conditionString = "";
				/*if (conditionObject.getName() != null)
					conditionString += "name LIKE '%" + conditionObject.getName() + "%' AND ";
				if (conditionObject.getUrl() != null)
					conditionString += "url LIKE '%" + conditionObject.getUrl() + "%' AND ";
				
				if (conditionString.length() > 5)
					conditionString = conditionString.substring(0, conditionString.length() - 5);*/
				
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
	 * 读取个人消息
	 */
	@Permission
	public void loadMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String[] params = new String[] {"id", "type", "content", "createTime"};
			
			List<Map<String, Object>> datas = messageService.load(params, "userId = '" + userId + "' ORDER BY createTime DESC");
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
	 * 个人消息条数
	 */
	@Permission
	public void countMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			int length = messageService.count("userId = '" + userId + "'");
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
	 * 删除个人消息
	 */
	@Permission
	public void deleteMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			boolean isSucceed = messageService.deleteByCondition("id = '" + id + "' AND userId = '" + userId + "'");
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
	 * 意见反馈
	 */
	@Permission
	public void reply() {
		try {
			if (!KaptchaUtil.checkCode(validCode)) {
				WebUtil.writeJson(WebUtil.JSON_WRONG_VALIDCODE);
				return;
			}
			
			if (content.length() < 15 && content.length() > 127) {
				WebUtil.writeJson("{\"result\":\"字数不符合要求\"}");
				return;
			} else {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS); // 由于发送消息需要一定时间，为了提升用户体验，在验证完准备发送前先把结果发给客户端
			}
			
			String[] params = new String[] {"id"};
			List<Map<String, Object>> datas = userService.load(params, "roleId = '" + User.ROLEID_SUPER_MANAGER + "'");
			
			/* 生成消息 */
			String messageContent = HtmlUtil.formatHtmlSpecialChars(content); // 防止XSS
			double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
			
			/* 向所有超级管理员发送一条消息 */
			Message messageObject = new Message();
			messageObject.setId(DataUtil.getUUID());
			messageObject.setType(Message.TYPE_INFO);
			messageObject.setContent(messageContent);
			messageObject.setCreateTime(messageDate);
			for (int i = 0, length = datas.size(); i < length; i++) {
				String currentUserId = (String) datas.get(i).get("id");
				messageObject.setUserId(currentUserId);
				messageService.save(messageObject);
			}
			
			MailUtil.sendToAdminInBackground("财务管理系统", "<p>意见反馈：</p><p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
}
