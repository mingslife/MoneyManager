package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.CommonDao;
import com.ming.entity.Permission;

/**
 * PermissionService
 * @author Ming
 * @description
 * @date 2015-07-10
 */
public class PermissionService implements IPermissionService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(Permission.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(Permission.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, Permission.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, Permission.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(Permission.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, Permission.class, condition);
	}
	
	@Override
	public boolean save(Permission obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(Permission obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(Permission obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(Permission.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(Permission.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(Permission.class, condition);
	}
	
	public PermissionService() {
	}
}
