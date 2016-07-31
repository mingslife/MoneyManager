package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.BaseDao;
import com.ming.dao.CommonDao;
import com.ming.entity.BillForIn;
import com.ming.entity.BillForOut;

/**
 * BillForInService
 * @author Ming
 * @description
 * @date 2015-03-24
 */
public class BillForInService implements IBillForInService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(BillForIn.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(BillForIn.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, BillForIn.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, BillForIn.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(BillForIn.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, BillForIn.class, condition);
	}
	
	@Override
	public boolean save(BillForIn obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(BillForIn obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(BillForIn obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(BillForIn.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(BillForIn.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(BillForIn.class, condition);
	}
	
	public BillForInService() {
	}
	
	public List<Map<String, Object>> loadSpecial(String condition, int curPage, int limit) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.amount, b.userName fromName, "
				+ "c.userName toName, d.userName recordName, a.createDate, a.remark FROM "
				+ "BillForIn a LEFT JOIN User b ON a.fromId = b.id LEFT JOIN User c ON a.toId "
				+ "= c.id LEFT JOIN User d ON a.recordId = d.id) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		sql += " ORDER BY date DESC LIMIT " + (curPage - 1) * limit + ", " + curPage * limit;
		List<Map<String, Object>> data = BaseDao.load(sql);
		return data;
	}
	
	public Map<String, Object> loadOneSpecial(String condition) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.amount, b.userName fromName, "
				+ "c.userName toName, d.userName recordName, a.createDate, a.remark FROM "
				+ "BillForIn a LEFT JOIN User b ON a.fromId = b.id LEFT JOIN User c ON a.toId "
				+ "= c.id LEFT JOIN User d ON a.recordId = d.id) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		List<Map<String, Object>> data = BaseDao.load(sql);
		if (data != null && data.size() == 1)
			return data.get(0);
		else
			return null;
	}
	
	public int countSpecial(String condition) {
		String sql = "SELECT COUNT(*) FROM (SELECT a.id, a.date, a.amount, b.userName fromName, "
				+ "c.userName toName, d.userName recordName, a.createDate, a.remark FROM "
				+ "BillForIn a LEFT JOIN User b ON a.fromId = b.id LEFT JOIN User c ON a.toId = "
				+ "c.id LEFT JOIN User d ON a.recordId = d.id) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		List<Map<String, Object>> data = BaseDao.load(sql);
		int length = -1;
		if (data.size() == 1) {
			Map<String, Object> map = data.get(0);
			if (map != null && map.size() == 1) {
				String count = null;
				try {
					for (Map.Entry<String, Object> value : map.entrySet())
						count = value.getValue().toString();
					length = Integer.parseInt(count);
				} catch (Exception e) {
					length = -1;
				}
			}
		}
		return length;
	}
	
	public double sumUp(String condition) {
		String sql = "SELECT SUM(amount) FROM (SELECT a.id, a.date, a.amount, b.userName fromName, "
				+ "c.userName toName, d.userName recordName, a.createDate, a.remark FROM "
				+ "BillForIn a LEFT JOIN User b ON a.fromId = b.id LEFT JOIN User c ON a.toId = "
				+ "c.id LEFT JOIN User d ON a.recordId = d.id) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		List<Map<String, Object>> data = BaseDao.load(sql);
		double sum = 0;
		if (data.size() == 1) {
			Map<String, Object> map = data.get(0);
			if (map != null && map.size() == 1) {
				String amount = null;
				try {
					for (Map.Entry<String, Object> value : map.entrySet())
						amount = value.getValue().toString();
					sum = Double.parseDouble(amount);
				} catch (Exception e) {
					sum = 0;
				}
			}
		}
		return sum;
	}
	
	public double sumUpMoney() {
		String sql = "SELECT (bin.amount - bout.amount) amount FROM (SELECT SUM(amount) amount FROM "
				+ "BillForIn) bin, (SELECT IFNULL(SUM(amount), 0) amount FROM BillForOut WHERE status = "
				+ BillForOut.STATUS_PAID + ") bout";
		List<Map<String, Object>> data = BaseDao.load(sql);
		double sum = 0;
		if (data.size() == 1) {
			Map<String, Object> map = data.get(0);
			if (map != null && map.size() == 1) {
				String amount = null;
				try {
					for (Map.Entry<String, Object> value : map.entrySet())
						amount = value.getValue().toString();
					sum = Double.parseDouble(amount);
				} catch (Exception e) {
					sum = 0;
				}
			}
		}
		return sum;
	}
}
