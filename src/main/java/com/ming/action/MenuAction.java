package com.ming.action;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ming.entity.Menu;
import com.ming.service.IMenuService;
import com.ming.service.MenuService;
import com.ming.util.Condition;
import com.ming.util.DataUtil;
import com.ming.util.Permission;
import com.ming.util.WebUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * MenuAction
 * @author Ming
 * @description
 * @date 2015-07-02
 */
public class MenuAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static final Gson GSON = new Gson();
	private static final IMenuService menuService = new MenuService();
	
	/* 开始配置数据接收 */
	private String id;
	private String menu;
	private int curPage;
	private int limit;
	private String condition;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
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
			String[] params = new String[] {"menuId", "name", "url", "href", "no", "roleId"};
			
			List<Map<String, Object>> datas = menuService.load(params, conditionString(), curPage, limit);
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
			int length = menuService.count(conditionString());
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
			String[] params = new String[] {"menuId", "name", "url", "href", "no", "roleId"};
			
			Map<String, Object> data = menuService.loadOne(params, "menuId = '" + id + "'");
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
			Menu menuObject = GSON.fromJson(menu, Menu.class);
			menuObject.setMenuId(DataUtil.getUUID());
			
			boolean isSucceed = menuService.save(menuObject);
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
			Menu menuObject = GSON.fromJson(menu, Menu.class);
			
			boolean isSucceed = menuService.update(menuObject);
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
			Menu menuObject = new Menu(id);
			
			boolean isSucceed = menuService.delete(menuObject);
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
				if (conditionObject.getName() != null)
					conditionString += "name LIKE '%" + conditionObject.getName() + "%' AND ";
				if (conditionObject.getUrl() != null)
					conditionString += "url LIKE '%" + conditionObject.getUrl() + "%' AND ";
				
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
	 * 获取个人菜单信息
	 */
	@Permission
	public void loadMine() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			Integer roleId = (Integer) session.get("roleId");
			
			String[] params = new String[] {"name", "href"};
			
			List<Map<String, Object>> datas = menuService.load(params, "roleId = '" + roleId + "' ORDER BY no");
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
}
