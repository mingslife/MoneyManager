package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.CommonDao;
import com.ming.entity.Page;

/**
 * PageService
 * @author Ming
 * @description
 * @date 2015-03-24
 */
public class PageService implements IPageService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(Page.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(Page.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, Page.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, Page.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(Page.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, Page.class, condition);
	}
	
	@Override
	public boolean save(Page obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(Page obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(Page obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(Page.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(Page.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(Page.class, condition);
	}
	
	public PageService() {
	}
}
