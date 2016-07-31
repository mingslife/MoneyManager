package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.entity.BillForIn;

public interface IBillForInService {
	public List<Map<String, Object>> load();
	public List<Map<String, Object>> load(String condition);
	public List<Map<String, Object>> load(String[] params, String condition);
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit);
	public Map<String, Object> loadOne(String condition);
	public Map<String, Object> loadOne(String[] params, String condition);
	public boolean save(BillForIn obj);
	public boolean update(BillForIn obj);
	public boolean delete(BillForIn obj);
	public boolean deleteByCondition(String condition);
	public int count();
	public int count(String condition);
	
	public List<Map<String, Object>> loadSpecial(String condition, int curPage, int limit);
	public Map<String, Object> loadOneSpecial(String condition);
	public int countSpecial(String condition);
	public double sumUp(String condition);
	public double sumUpMoney();
}
