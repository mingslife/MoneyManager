package com.ming.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ming.entity.BillForOut;
import com.ming.entity.Message;
import com.ming.entity.User;
import com.ming.service.BillForOutService;
import com.ming.service.IBillForOutService;
import com.ming.service.IMessageService;
import com.ming.service.IUserService;
import com.ming.service.MessageService;
import com.ming.service.UserService;
import com.ming.util.Condition;
import com.ming.util.ConsoleMessage;
import com.ming.util.DataSecurity;
import com.ming.util.DataUtil;
import com.ming.util.KaptchaUtil;
import com.ming.util.MailUtil;
import com.ming.util.Permission;
import com.ming.util.WebUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static final Gson GSON = new Gson();
	private static final IUserService userService = new UserService();
	private static final IMessageService messageService = new MessageService();
	private static final IBillForOutService billForOutService = new BillForOutService();
	
	/* 开始配置数据接收 */
	private String id;
	private String user;
	private int curPage;
	private int limit;
	private String condition;
	private String username;
	private String password;
	private String loginName;
	private String validCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
			String[] params = new String[] {"id", "userName", "sex", "birthday", "email", "qq", "roleId", "isActive", "createDate"};
			
			List<Map<String, Object>> datas = userService.load(params, conditionString(), curPage, limit);
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
			int length = userService.count(conditionString());
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
			String[] params = new String[] {"id", "userName", "sex", "birthday", "email", "qq", "roleId", "isActive", "createDate"};
			
			Map<String, Object> data = userService.loadOne(params, "id = '" + id + "'");
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
			User userObject = GSON.fromJson(user, User.class);
			userObject.setId(DataUtil.getUUID());
			userObject.setPassword(DataSecurity.stringMD5(userObject.getPassword()));
			userObject.setCreateDate(Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMdd")));
			
			String loginName = userObject.getLoginName();
			if (!check(loginName)) {
				userObject.setLoginName(DataSecurity.stringMD5(loginName));
			} else {
				WebUtil.writeJson("{\"result\":\"用户已存在\"}");
				return;
			}
			
			boolean isSucceed = userService.save(userObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
			
			if (userObject.getIsActive() == User.ISACTIVE_YES) {
				/* 生成消息 */
				String messageContent = "尊敬的" + userObject.getUserName() + "，欢迎您使用本系统";
				double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
				
				/* 向新用户发送一条欢迎消息 */
				Message messageObject = new Message();
				messageObject.setId(DataUtil.getUUID());
				messageObject.setType(Message.TYPE_INFO);
				messageObject.setContent(messageContent);
				messageObject.setCreateTime(messageDate);
				messageObject.setUserId(userObject.getId());
				System.out.println(messageService.save(messageObject));
				
				/* 向新用户发送一条邮件 */
				MailUtil.sendInBackground(userObject.getEmail(), "财务管理系统", "<p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
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
			User userObject = GSON.fromJson(user, User.class);
			userObject.setLoginName(null);
			
			String password = userObject.getPassword();
			userObject.setPassword(password == null ? null : DataSecurity.stringMD5(password));
			
			boolean isSucceed = userService.update(userObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
			
			if (userObject.getIsActive() == User.ISACTIVE_YES) {
				/* 生成消息 */
				String messageContent = "尊敬的" + userObject.getUserName() + "，欢迎您使用本系统";
				double messageDate = Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
				
				/* 向新用户发送一条欢迎消息 */
				Message messageObject = new Message();
				messageObject.setId(DataUtil.getUUID());
				messageObject.setType(Message.TYPE_INFO);
				messageObject.setContent(messageContent);
				messageObject.setCreateTime(messageDate);
				messageObject.setUserId(userObject.getId());
				messageService.save(messageObject);
				
				/* 向新用户发送一条邮件 */
				MailUtil.sendInBackground(userObject.getEmail(), "财务管理系统", "<p>" + messageContent + "</p><p>" + DataUtil.dateTimeFormat(messageDate) + "</p>");
			} else {
				// 清空未激活用户的消息
				messageService.deleteByCondition("userId = '" + userObject.getId() + "'");
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
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			if (id.trim().equals(userId)) {
				WebUtil.writeJson("{\"result\":\"系统禁止删除自己的账号\"}");
				return;
			}
			
			User userObject = new User(id);
			
			boolean isSucceed = userService.delete(userObject);
			if (isSucceed) {
				WebUtil.writeJson(WebUtil.JSON_SUCCESS);
			} else {
				WebUtil.writeJson(WebUtil.JSON_ERROR);
			}
			
			// 删除用户时清空其用户的消息
			messageService.deleteByCondition("userId = '" + id + "'");
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
				if (conditionObject.getUserName() != null)
					conditionString += "userName LIKE '%" + conditionObject.getUserName() + "%' AND ";
				
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
	 * 登录
	 */
	public void login() {
		try {
			if (!KaptchaUtil.checkCode(validCode)) {
				WebUtil.writeJson(WebUtil.JSON_WRONG_VALIDCODE);
				return;
			}
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			String[] params = new String[] {"id", "userName", "roleId", "isActive"};
			Map<String, Object> user = userService.loadOne(params, "loginName = '" + DataSecurity.stringMD5(username) + "' AND password = '" + DataSecurity.stringMD5(password) + "'");
			if (user != null) {
				if (user.get("isActive").equals(0)) {
					WebUtil.writeJson("{\"result\":\"账号未激活，请联系管理员\"}");
				} else {
					String userId = (String) user.get("id");
					Integer roleId = (Integer) user.get("roleId");
					String userName = (String) user.get("userName");
					session.put("userId", userId);
					session.put("roleId", roleId);
					session.put("userName", userName);
					ConsoleMessage.info(userId + "登录");
					WebUtil.writeJson("{\"result\":\"success\",\"url\":\"main.html\"}"); // 防止暴露后台地址，一定程度上防止攻击
				}
			} else {
				WebUtil.writeJson("{\"result\":\"账号或密码错误\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 注销
	 */
	@Permission
	public void logout() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			ConsoleMessage.info(session.get("userId") + "注销");
			session.clear();
			WebUtil.writeJson(WebUtil.JSON_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 读取个人信息
	 */
	@Permission
	public void loadMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String[] params = new String[] {"userName", "sex", "birthday", "email", "qq", "roleId", "createDate"};
			
			Map<String, Object> data = userService.loadOne(params, "id = '" + userId + "'");
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
	 * 修改个人信息
	 */
	@Permission
	public void editMine() {
		try {
			if (!KaptchaUtil.checkCode(validCode)) {
				WebUtil.writeJson(WebUtil.JSON_WRONG_VALIDCODE);
				return;
			}
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			
			User userObject = GSON.fromJson(user, User.class);
			userObject.setId(userId);
			userObject.setLoginName(null);
			userObject.setUserName(null);
			userObject.setSex(null);
			userObject.setRoleId(null);
			userObject.setIsActive(null);
			userObject.setCreateDate(null);
			
			if (password == null) {
				userObject.setPassword(null);
			} else if (DataSecurity.stringMD5(password).equals(userService.loadOne("id = '" + userId + "'").get("password"))) {
				userObject.setPassword(DataSecurity.stringMD5(userObject.getPassword()));
			} else {
				WebUtil.writeJson("{\"result\":\"密码错误\"}");
				return;
			}
			
			boolean isSucceed = userService.update(userObject);
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
	 * 加载用户列表
	 */
	@Permission
	public void loadAll() {
		try {
			String[] params = {"id", "userName"};
			
			List<Map<String, Object>> datas = userService.load(params, "isActive = '1'");
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
	 * 登录名查重
	 */
	@Permission
	public void check() {
		try {
			WebUtil.writeJson("{\"result\":" + check(loginName) + "}");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 登录名查重
	 * @param loginName 登录名
	 * @return 是否存在相同登录名的用户
	 */
	private boolean check(String loginName) {
		Map<String, Object> userMap = userService.loadOne("loginName = '" + DataSecurity.stringMD5(loginName) + "'");
		if (userMap == null)
			return false;
		else
			return true;
	}
	
	/**
	 * 获取当前用户ID
	 */
	@Permission
	public void currentId() {
		try {
			String userId = (String) ActionContext.getContext().getSession().get("userId");
			if (userId != null)
				WebUtil.writeJson("{\"id\":\"" + userId + "\"}");
			else
				WebUtil.writeJson(WebUtil.JSON_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 获取首页数据
	 */
	@Permission
	public void welcome() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String userId = (String) session.get("userId");
			String userName = (String) session.get("userName");
			
			Integer numberOfUncheckResult = null;
			Double amountOfUncheckResult = null;
			Integer numberOfUnpaidResult = null;
			Double amountOfUnpaidResult = null;
			int numberOfUncheck = billForOutService.countMineSpecial(userId, "status = " + BillForOut.STATUS_UNCHECK);
			if (numberOfUncheck != 0) {
				double amountOfUncheck = billForOutService.sumUpMine(userId, "status = " + BillForOut.STATUS_UNCHECK);
				numberOfUncheckResult = numberOfUncheck;
				amountOfUncheckResult = amountOfUncheck;
			}
			int numberOfUnpaid = billForOutService.countMineSpecial(userId, "status = " + BillForOut.STATUS_UNPAID);
			if (numberOfUnpaid != 0) {
				double amountOfUnpaid = billForOutService.sumUpMine(userId, "status = " + BillForOut.STATUS_UNPAID);
				numberOfUnpaidResult = numberOfUnpaid;
				amountOfUnpaidResult = amountOfUnpaid;
			}
			
			WebUtil.writeJson("{\"numberOfUncheck\":" + numberOfUncheckResult + ",\"amountOfUncheck\":" + amountOfUncheckResult
							+ ",\"numberOfUnpaid\":" + numberOfUnpaidResult + ",\"amountOfUnpaid\":" + amountOfUnpaidResult
							+ ",\"userName\":\"" + userName + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 测试用户是否已经登录
	 */
	@Permission
	public void test() {
//		try {
//			for (int i = 0; i < 1000; i++)
//				userService.load();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		WebUtil.writeText("数据库并发测试结束，谢谢！！");
		WebUtil.writeJson("{\"result\":\"success\",\"url\":\"main.html\"}");
	}
}
