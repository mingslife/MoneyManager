package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.CommonDao;
import com.ming.entity.Role;

/**
 * RoleService
 * @author Ming
 * @description
 * @date 2015-03-24
 */
public class RoleService implements IRoleService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(Role.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(Role.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, Role.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, Role.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(Role.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, Role.class, condition);
	}
	
	@Override
	public boolean save(Role obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(Role obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(Role obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(Role.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(Role.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(Role.class, condition);
	}
	
	public RoleService() {
	}
}
