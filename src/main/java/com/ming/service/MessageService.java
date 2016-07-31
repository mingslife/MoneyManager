package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.CommonDao;
import com.ming.entity.Message;

/**
 * MessageService
 * @author Ming
 * @description
 * @date 2015-07-10
 */
public class MessageService implements IMessageService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(Message.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(Message.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, Message.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, Message.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(Message.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, Message.class, condition);
	}
	
	@Override
	public boolean save(Message obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(Message obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(Message obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(Message.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(Message.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(Message.class, condition);
	}
	
	public MessageService() {
	}
}
