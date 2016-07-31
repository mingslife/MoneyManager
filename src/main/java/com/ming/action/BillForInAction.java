package com.ming.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ming.entity.BillForIn;
import com.ming.entity.BillForOut;
import com.ming.service.BillForInService;
import com.ming.service.BillForOutService;
import com.ming.service.IBillForInService;
import com.ming.service.IBillForOutService;
import com.ming.util.Condition;
import com.ming.util.DataUtil;
import com.ming.util.Permission;
import com.ming.util.WebUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BillForInAction
 * @author Ming
 * @description
 * @date 2015-07-10
 */
public class BillForInAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static final Gson GSON = new Gson();
	private static final IBillForInService billForInService = new BillForInService();
	private static final IBillForOutService billForOutService = new BillForOutService();
	
	/* 开始配置数据接收 */
	private String id;
	private String billForIn;
	private int curPage;
	private int limit;
	private String condition;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillForIn() {
		return billForIn;
	}

	public void setBillForIn(String billForIn) {
		this.billForIn = billForIn;
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
	/* 结束配置数据接收 */

	/**
	 * 读取多条数据
	 */
	@Permission
	public void load() {
		try {
			List<Map<String, Object>> datas = billForInService.loadSpecial(conditionString(), curPage, limit);
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
			int length = billForInService.countSpecial(conditionString());
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
			String[] params = new String[] {"id", "date", "amount", "fromId", "toId", "recordId", "remark"};
			
			Map<String, Object> data = billForInService.loadOne(params, "id = '" + id + "'");
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
			BillForIn billForInObject = GSON.fromJson(billForIn, BillForIn.class);
			billForInObject.setId(DataUtil.getUUID());
			billForInObject.setCreateDate(Double.parseDouble(DataUtil.getDateString(new Date(), "yyyyMMdd")));
			
			boolean isSucceed = billForInService.save(billForInObject);
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
			BillForIn billForInObject = GSON.fromJson(billForIn, BillForIn.class);
			
			boolean isSucceed = billForInService.update(billForInObject);
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
			BillForIn billForInObject = new BillForIn(id);
			
			boolean isSucceed = billForInService.delete(billForInObject);
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
				if (conditionObject.getToName() != null)
					conditionString += "toName LIKE '%" + conditionObject.getToName() + "%' AND ";
				
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
	 * 入账金额统计
	 */
	@Permission
	public void sumUp() {
		try {
			double sum = billForInService.sumUp(conditionString());
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
	public void sumUpMoney() {
		try {
			double sum = billForInService.sumUpMoney();
			String json = "{\"amount\":" + sum + "}";
			WebUtil.writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.writeJson(WebUtil.JSON_ERROR);
		}
	}
	
	/**
	 * 查看所有还需金额
	 */
	@Permission
	public void sumUpNeed() {
		try {
			double leftMoney = billForInService.sumUpMoney();
			double unpaidMoney = billForOutService.sumUp("status = " + BillForOut.STATUS_UNPAID);
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
