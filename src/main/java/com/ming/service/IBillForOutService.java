package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.entity.BillForOut;

public interface IBillForOutService {
	public List<Map<String, Object>> load();
	public List<Map<String, Object>> load(String condition);
	public List<Map<String, Object>> load(String[] params, String condition);
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit);
	public Map<String, Object> loadOne(String condition);
	public Map<String, Object> loadOne(String[] params, String condition);
	public boolean save(BillForOut obj);
	public boolean update(BillForOut obj);
	public boolean delete(BillForOut obj);
	public boolean deleteByCondition(String condition);
	public int count();
	public int count(String condition);
	
	public List<Map<String, Object>> loadSpecial(String condition, int curPage, int limit);
	public Map<String, Object> loadOneSpecial(String condition);
	public int countSpecial(String condition);
	public List<Map<String, Object>> loadMineSpecial(String userId, String condition, int curPage, int limit);
	public int countMineSpecial(String userId, String condition);
	public List<Map<String, Object>> loadReviewSpecial(String condition, int curPage, int limit);
	public Map<String, Object> loadOneReviewSpecial(String condition);
	public int countReviewSpecial(String condition);
	public List<Map<String, Object>> loadPaySpecial(String userId, String condition, int curPage, int limit);
	public int countPaySpecial(String userId, String condition);
	public double sumUp(String condition);
	public double sumUpMine(String userId, String condition);
	public double sumUpReview(String condition);
	public double sumUpPay(String userId, String condition);
}
