package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.CommonDao;
import com.ming.entity.Menu;

/**
 * MenuService
 * @author Ming
 * @description
 * @date 2015-06-12
 */
public class MenuService implements IMenuService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(Menu.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(Menu.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, Menu.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, Menu.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(Menu.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, Menu.class, condition);
	}
	
	@Override
	public boolean save(Menu obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(Menu obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(Menu obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(Menu.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(Menu.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(Menu.class, condition);
	}
	
	public MenuService() {
	}
}
