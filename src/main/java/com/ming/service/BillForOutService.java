package com.ming.service;

import java.util.List;
import java.util.Map;

import com.ming.dao.BaseDao;
import com.ming.dao.CommonDao;
import com.ming.entity.BillForOut;

/**
 * BillForOutService
 * @author Ming
 * @description
 * @date 2015-03-24
 */
public class BillForOutService implements IBillForOutService {
	private static final CommonDao db = new CommonDao();
	
	@Override
	public List<Map<String, Object>> load() {
		return db.load(BillForOut.class);
	}
	
	@Override
	public List<Map<String, Object>> load(String condition) {
		return db.load(BillForOut.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition) {
		return db.load(params, BillForOut.class, condition);
	}
	
	@Override
	public List<Map<String, Object>> load(String[] params, String condition, int curPage, int limit) {
		return db.load(params, BillForOut.class, condition, curPage, limit);
	}
	
	@Override
	public Map<String, Object> loadOne(String condition) {
		return db.loadOne(BillForOut.class, condition);
	}
	
	@Override
	public Map<String, Object> loadOne(String[] params, String condition) {
		return db.loadOne(params, BillForOut.class, condition);
	}
	
	@Override
	public boolean save(BillForOut obj) {
		return db.insert(obj);
	}
	
	@Override
	public boolean update(BillForOut obj) {
		return db.update(obj);
	}
	
	@Override
	public boolean delete(BillForOut obj) {
		return db.delete(obj);
	}
	
	@Override
	public boolean deleteByCondition(String condition) {
		return db.delete(BillForOut.class, condition);
	}
	
	@Override
	public int count() {
		return db.count(BillForOut.class);
	}
	
	@Override
	public int count(String condition) {
		return db.count(BillForOut.class, condition);
	}
	
	public BillForOutService() {
	}
	
	public List<Map<String, Object>> loadSpecial(String condition, int curPage, int limit) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.event, a.amount, b.userName payName, "
				+ "a.status, c.userName recordName, a.createDate, a.remark FROM BillForOut a LEFT "
				+ "JOIN User b ON a.payId = b.id LEFT JOIN User c ON a.recordId = c.id) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		sql += " ORDER BY date DESC LIMIT " + (curPage - 1) * limit + ", " + curPage * limit;
		List<Map<String, Object>> data = BaseDao.load(sql);
		return data;
	}
	
	public Map<String, Object> loadOneSpecial(String condition) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.event, a.amount, b.userName payName, "
				+ "a.status, c.userName recordName, a.createDate FROM BillForOut a LEFT JOIN User "
				+ "b ON a.payId = b.id LEFT JOIN User c ON a.recordId = c.id) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		List<Map<String, Object>> data = BaseDao.load(sql);
		if (data != null && data.size() == 1)
			return data.get(0);
		else
			return null;
	}
	
	public int countSpecial(String condition) {
		String sql = "SELECT COUNT(*) FROM (SELECT a.date, a.status, b.userName payName "
				+ "FROM BillForOut a LEFT JOIN User b ON a.payId = b.id LEFT JOIN User c ON "
				+ "a.recordId = c.id) result";
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
	
	public List<Map<String, Object>> loadMineSpecial(String userId, String condition, int curPage, int limit) {
		String sql = "SELECT * FROM (SELECT a.date, a.event, a.amount, a.status, b.userName "
				+ "recordName, a.createDate, a.remark FROM BillForOut a LEFT JOIN User b ON "
				+ "a.recordId = b.id WHERE a.payId = '" + userId + "') result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		sql += " ORDER BY date DESC LIMIT " + (curPage - 1) * limit + ", " + curPage * limit;
		List<Map<String, Object>> data = BaseDao.load(sql);
		return data;
	}
	
	public int countMineSpecial(String userId, String condition) {
		String sql = "SELECT COUNT(*) FROM (SELECT a.date, a.event, a.amount, a.status, "
				+ "b.userName recordName, a.createDate, a.remark FROM BillForOut a LEFT JOIN "
				+ "User b ON a.recordId = b.id WHERE a.payId = '" + userId + "') result";
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
	
	public List<Map<String, Object>> loadReviewSpecial(String condition, int curPage, int limit) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.event, a.amount, b.userName payName, "
				+ "a.createDate, a.remark FROM BillForOut a LEFT JOIN User b ON a.payId = b.id WHERE "
				+ "a.status = " + BillForOut.STATUS_UNCHECK + ") result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		sql += " ORDER BY date DESC LIMIT " + (curPage - 1) * limit + ", " + curPage * limit;
		List<Map<String, Object>> data = BaseDao.load(sql);
		return data;
	}
	
	public Map<String, Object> loadOneReviewSpecial(String condition) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.event, a.amount, b.userName payName, "
				+ "a.createDate FROM BillForOut a LEFT JOIN User b ON a.payId = b.id WHERE a.status = "
				+ BillForOut.STATUS_UNCHECK + ") result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		List<Map<String, Object>> data = BaseDao.load(sql);
		if (data != null && data.size() == 1)
			return data.get(0);
		else
			return null;
	}
	
	public int countReviewSpecial(String condition) {
		String sql = "SELECT COUNT(*) FROM (SELECT a.date, b.userName payName FROM BillForOut a LEFT "
				+ "JOIN User b ON a.payId = b.id WHERE a.status = " + BillForOut.STATUS_UNCHECK + ") "
				+ "result";
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
	
	public List<Map<String, Object>> loadPaySpecial(String userId, String condition, int curPage, int limit) {
		String sql = "SELECT * FROM (SELECT a.id, a.date, a.event, a.amount, a.status, b.userName "
				+ "payName, a.createDate, a.remark FROM BillForOut a LEFT JOIN User b ON "
				+ "a.payId = b.id WHERE a.recordId = '" + userId + "' AND a.status IN ("
				+ BillForOut.STATUS_UNPAID + ", " + BillForOut.STATUS_PAID + ")) result";
		if (null != condition && !"".equals(condition))
			sql += " WHERE " + condition;
		sql += " ORDER BY date DESC LIMIT " + (curPage - 1) * limit + ", " + curPage * limit;
		List<Map<String, Object>> data = BaseDao.load(sql);
		return data;
	}
	
	public int countPaySpecial(String userId, String condition) {
		String sql = "SELECT COUNT(*) FROM (SELECT a.date, a.event, a.amount, a.status, "
				+ "b.userName payName, a.createDate, a.remark FROM BillForOut a LEFT JOIN "
				+ "User b ON a.payId = b.id WHERE a.recordId = '" + userId + "' AND a.status "
				+ "IN (" + BillForOut.STATUS_UNPAID + ", " + BillForOut.STATUS_PAID + ")) result";
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
		String sql = "SELECT SUM(amount) FROM (SELECT a.date, a.status, a.amount, b.userName payName "
				+ "FROM BillForOut a LEFT JOIN User b ON a.payId = b.id LEFT JOIN User c ON "
				+ "a.recordId = c.id) result";
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
	
	public double sumUpMine(String userId, String condition) {
		String sql = "SELECT SUM(amount) FROM (SELECT a.date, a.event, a.amount, a.status, "
				+ "b.userName recordName, a.createDate, a.remark FROM BillForOut a LEFT JOIN "
				+ "User b ON a.recordId = b.id WHERE a.payId = '" + userId + "') result";
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
	
	public double sumUpReview(String condition) {
		String sql = "SELECT SUM(amount) FROM (SELECT a.date, a.amount, b.userName payName FROM BillForOut a LEFT "
				+ "JOIN User b ON a.payId = b.id WHERE a.status = " + BillForOut.STATUS_UNCHECK + ") "
				+ "result";
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
	
	public double sumUpPay(String userId, String condition) {
		String sql = "SELECT SUM(amount) FROM (SELECT a.date, a.event, a.amount, a.status, "
				+ "b.userName payName, a.createDate, a.remark FROM BillForOut a LEFT JOIN "
				+ "User b ON a.payId = b.id WHERE a.recordId = '" + userId + "' AND a.status "
				+ "IN (" + BillForOut.STATUS_UNPAID + ", " + BillForOut.STATUS_PAID + ")) result";
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
}
