package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.entity.User;

public interface IUserService {
	public List<Map<String, Object>> load();
	public List<Map<String, Object>> load(String condition);
	public List<Map<String, Object>> load(String[] params, String condition);
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit);
	public Map<String, Object> loadOne(String condition);
	public Map<String, Object> loadOne(String[] params, String condition);
	public boolean save(User obj);
	public boolean update(User obj);
	public boolean delete(User obj);
	public boolean deleteByCondition(String condition);
	public int count();
	public int count(String condition);
}
