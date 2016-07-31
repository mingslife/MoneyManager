package com.ming.dao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ming.util.ConnectionPool;
import com.ming.util.ConsoleMessage;
import com.ming.util.SqlUtil;

/**
 * 公共数据库连接类
 * @author Ming
 * @description 提供数据操作的公共方法
 * @date 2015-03-11
 */
public class CommonDao {
	private final static int UNKNOWN = 0;
	private final static int MYSQL = 100;
	private final static int SQLSERVER = 101;
	private final static int ORACLE = 102;
	
	/**
	 * 数据库类型
	 */
	private static int dbType = UNKNOWN;
	
	/**
	 * 向数据库中插入一条数据
	 * @param obj 实体对象
	 * @return 是否插入成功
	 */
	public boolean insert(final Object obj) {
		String sql = SqlUtil.insert(obj);
		return BaseDao.execute(sql);
	}
	
	/**
	 * 向数据库中更新一条数据
	 * @param obj 实体对象
	 * @return 是否更新成功
	 */
	public boolean update(final Object obj) {
		String sql = SqlUtil.update(obj);
		return BaseDao.execute(sql);
	}
	
	/**
	 * 向数据库中删除一条数据
	 * @param obj 实体对象
	 * @return 是否删除成功
	 */
	public boolean delete(final Object obj) {
		String sql = SqlUtil.delete(obj);
		return BaseDao.execute(sql);
	}
	
	/**
	 * 向数据库中删除一条数据
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 是否删除成功
	 */
	public boolean delete(final Class<?> cls, final String condition) {
		String sql = SqlUtil.delete(cls, condition);
		return BaseDao.execute(sql);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param cls 实体类对象
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final Class<?> cls) {
		String sql = SqlUtil.load(cls);
		return BaseDao.load(sql);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final Class<?> cls, final String condition) {
		String sql = SqlUtil.load(cls, condition);
		return BaseDao.load(sql);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @param curPage 当前页数
	 * @param limit 每页条数
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final Class<?> cls, final String condition, final int curPage, final int limit) {
		String sql = SqlUtil.load(cls, condition);
		return this.loadWithLimit(sql, condition, curPage, limit);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param params 字段名
	 * @param cls 实体类对象
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final String[] params, final Class<?> cls) {
		String sql = SqlUtil.load(params, cls);
		return BaseDao.load(sql);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param params 字段名
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final String[] params, final Class<?> cls, final String condition) {
		String sql = SqlUtil.load(params, cls, condition);
		return BaseDao.load(sql);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param params 字段名
	 * @param cls 实体类对象
	 * @param curPage 当前页数
	 * @param limit 每页条数
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final String[] params, final Class<?> cls, final int curPage, final int limit) {
		String sql = SqlUtil.load(params, cls);
		return this.loadWithLimit(sql, null, curPage, limit);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param params 字段名
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @param curPage 当前页数
	 * @param limit 每页条数
	 * @return 数据列表
	 */
	public List<Map<String, Object>> load(final String[] params, final Class<?> cls, final String condition, final int curPage, final int limit) {
		String sql = SqlUtil.load(params, cls, condition);
		return this.loadWithLimit(sql, condition, curPage, limit);
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param cls 实体类对象
	 * @return 数据映射
	 */
	public Map<String, Object> loadOne(final Class<?> cls) {
		String sql = SqlUtil.load(cls);
		return this.loadOne(sql);
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 数据映射
	 */
	public Map<String, Object> loadOne(final Class<?> cls, final String condition) {
		String sql = SqlUtil.load(cls, condition);
		return this.loadOne(sql);
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param params 字段名
	 * @param cls 实体类对象
	 * @return 数据映射
	 */
	public Map<String, Object> loadOne(final String[] params, final Class<?> cls) {
		String sql = SqlUtil.load(params, cls);
		return this.loadOne(sql);
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param params 字段名
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 数据映射
	 */
	public Map<String, Object> loadOne(final String[] params, final Class<?> cls, final String condition) {
		String sql = SqlUtil.load(params, cls, condition);
		return this.loadOne(sql);
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param cls 实体类对象
	 * @return 实体对象列表
	 */
	public <T> List<T> loadEntities(final Class<T> cls) {
		List<Map<String, Object>> list = load(cls);
		List<T> entities = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			T entity = this.getEntity(cls, map);
			entities.add(entity);
		}
		return entities;
	}
	
	/**
	 * 从数据库中读取若干条
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 实体对象列表
	 */
	public <T> List<T> loadEntities(final Class<T> cls, final String condition) {
		List<Map<String, Object>> list = load(cls, condition);
		List<T> entities = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			T entity = this.getEntity(cls, map);
			entities.add(entity);
		}
		return entities;
	}
	
	/**
	 * 从数据库中读取若干条数据
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @param curPage 当前页数
	 * @param limit 每页条数
	 * @return 实体对象列表
	 */
	public <T> List<T> loadEntities(final Class<T> cls, final String condition, final int curPage, final int limit) {
		List<Map<String, Object>> list = load(cls, condition, curPage, limit);
		List<T> entities = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			T entity = this.getEntity(cls, map);
			entities.add(entity);
		}
		return entities;
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param cls 实体类对象
	 * @return 实体对象
	 */
	public <T> T loadEntity(final Class<T> cls) {
		Map<String, Object> map = loadOne(cls);
		return this.getEntity(cls, map);
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 实体对象
	 */
	public <T> T loadEntity(final Class<T> cls, final String condition) {
		Map<String, Object> map = loadOne(cls, condition);
		return this.getEntity(cls, map);
	}
	
	/**
	 * 从数据库中读取数目
	 * @param cls 实体类对象
	 * @return 数目
	 */
	public int count(final Class<?> cls) {
		return count(cls, null);
	}
	
	/**
	 * 从数据库中读取数目
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return 数目
	 */
	public int count(final Class<?> cls, final String condition) {
		String sql = SqlUtil.count(cls, condition);
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
	
	/**
	 * 从数据库中读取若干条数据
	 * @param sql 未包含分页的SQL语句
	 * @param condition 条件
	 * @param curPage 当前页数
	 * @param limit 每页条数
	 * @return
	 */
	private List<Map<String, Object>> loadWithLimit(String sql, final String condition, final int curPage, final int limit) {
		List<Map<String, Object>> data = null;
		switch (getDBType()) {
		case MYSQL:
			{
				sql += " LIMIT " + (curPage - 1) * limit + ", " + limit;
				data = BaseDao.load(sql);
				break;
			}
		case SQLSERVER:
			{
				sql = sql.substring(5);
				sql = "SELECT TOP " + curPage * limit + sql;
				data = BaseDao.load(sql);
				for (int i = 0, length = (curPage - 1) * limit; i < length; i++)
					data.remove(0);
				break;
			}
		case ORACLE:
			{
				sql = sql.substring(5);
				sql = "SELECT * FROM (SELECT ROWNUM RN," + sql + (!(condition == null || "".equals(condition)) ? " AND " : " WHERE ") + "ROWNUM >= " + ") WHERE RN >= " + curPage * limit;
				data = BaseDao.load(sql);
				break;
			}
		}
		return data;
	}
	
	/**
	 * 从数据库中读取一条数据
	 * @param sql SQL语句
	 * @return 数据映射
	 */
	private Map<String, Object> loadOne(final String sql) {
		List<Map<String, Object>> data = BaseDao.load(sql);
		if (data != null && data.size() == 1)
			return data.get(0);
		else
			return null;
	}
	
	/**
	 * 获取实体对象，参考http://bbs.csdn.net/topics/390386157?page=1
	 * @param cls 实体类对象
	 * @param map 实体数据映射
	 * @return 实体对象
	 */
	private <T> T getEntity(final Class<T> cls, final Map<String, Object> map) {
		T entity = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(cls); // 获取类属性
			entity = cls.newInstance(); // 创建 JavaBean 对象

			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();

				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);

					Object[] args = new Object[1];
					args[0] = value;

					descriptor.getWriteMethod().invoke(entity, args);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 获取数据库类型
	 * @return 数据库类型
	 */
	private int getDBType() {
		if (dbType == UNKNOWN) {
			String driverName = ConnectionPool.getDriverName();
			if ("com.mysql.jdbc.Driver".equals(driverName) || "org.gjt.mm.mysql.Driver".equals(driverName))
				dbType = MYSQL;
			else if ("com.microsoft.jdbc.sqlserver.SQLServerDriver".equals(driverName))
				dbType = SQLSERVER;
			else if ("oracle.jdbc.OracleDriver".equals(driverName) || "oracle.jdbc.driver.OracleDriver".equals(driverName))
				dbType = ORACLE;
			else
				ConsoleMessage.error("遇到暂不支持的数据库");
		}
		return dbType;
	}
	
	public CommonDao() {
	}
}
