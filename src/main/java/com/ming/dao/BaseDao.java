package com.ming.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ming.util.ConnectionPool;
import com.ming.util.ConsoleMessage;

/**
 * 基础数据库连接类
 * @author Ming
 * @description 连接数据库和提供基础方法
 * @date 2015-02-21
 */
public class BaseDao {
	/**
	 * 数据库连接池
	 */
	final static ConnectionPool connectionPool = new ConnectionPool();
	
	/**
	 * 执行SQL语句进行数据库查询并返回数据
	 * @param sql SQL语句
	 * @return
	 */
	public static List<Map<String, Object>> load(final String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int numberOfColumns = resultSetMetaData.getColumnCount();
			
			// 获取字段名
			String[] keys = new String[numberOfColumns];
			for (int i = 0; i < numberOfColumns; i++)
				keys[i] = resultSetMetaData.getColumnLabel(i + 1);
			
			// 获取对应的值
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < numberOfColumns; i++)
					map.put(keys[i], resultSet.getObject(i + 1));
				
				list.add(map);
			}
			
			ConsoleMessage.info(sql);
		} catch (Exception e) {
			ConsoleMessage.warn("数据库操作失败");
			e.printStackTrace();
			
			// 关闭连接
			closeAll(connection, statement);
			return null;
		}
		
		// 关闭连接
		closeAll(connection, statement);
		
		return list;
	}
	
	/**
	 * 执行SQL语句进行数据库操作并返回是否成功
	 * @param sql
	 * @return 是否执行成功
	 */
	public static boolean execute(final String sql) {
		boolean result = false;
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.createStatement();
			statement.execute(sql);
			ConsoleMessage.info(sql);
			result = true;
		} catch (SQLException e) {
			ConsoleMessage.warn("数据库操作失败");
			e.printStackTrace();
			
			// 关闭连接
			closeAll(connection, statement);
			return false;
		}
		
		// 关闭连接
		closeAll(connection, statement);
		
		return result;
	}
	
	/**
	 * 获取数据库连接
	 * @return 数据库连接
	 */
	private static Connection getConnection() {
		Connection connection = null;
		try {
//			connectionPool.refreshConnections();
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 关闭数据库连接及表连接
	 * @param connection 数据库连接
	 * @param statement 表连接
	 */
	private static void closeAll(Connection connection, Statement statement) {
		try {
			if (connection != null && statement != null) {
				statement.close();
				connectionPool.returnConnection(connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
