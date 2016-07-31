package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.CommonDao;
import com.ming.entity.User;

/**
 * UserService
 * @author Ming
 * @description
 * @date 2015-03-24
 */
public class UserService implements IUserService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(User.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(User.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, User.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, User.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(User.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, User.class, condition);
	}
	
	@Override
	public boolean save(User obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(User obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(User obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(User.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(User.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(User.class, condition);
	}
	
	public UserService() {
	}
}
