package com.ming.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * SQL语句生成类
 * @author Ming
 * @description 用于生成建表、插入、更新、删除、查询实体类的SQL语句
 * @date 2015-03-10
 */
public class SqlUtil {
	/**
	 * 生成新增的SQL语句
	 * @param obj 实体对象
	 * @return SQL语句
	 */
	public static String insert(final Object obj) {
		try {
			StringBuilder result = new StringBuilder();
			Class<?> cls = obj.getClass();
			Field[] fields = cls.getDeclaredFields();
			if (fields.length == 0)
				throw new Exception("No data to insert");
			result.append("INSERT INTO " + cls.getSimpleName() + "(");
			StringBuilder values = new StringBuilder();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) { // 忽略静态成员
					String fieldName = field.getName();
					result.append(fieldName + ", "); // 加上字段名可以去除数据库字段顺序带来的影响，增加容错性
					Method getter = cls.getMethod(getterName(fieldName));
					Object getterResult = getter.invoke(obj);
					if (getterResult != null)
						values.append("'" + getterResult.toString() + "', ");
					else
						values.append("'', ");
				}
			}
			values.setLength(values.length() - 2);
			result.setLength(result.length() - 2);
			result.append(") VALUES (" + values.toString() + ")");
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成更新的SQL语句
	 * @param obj 实体对象
	 * @return SQL语句
	 */
	public static String update(final Object obj) {
		try {
			StringBuilder result = new StringBuilder();
			Map<String, Object> ids = new HashMap<String, Object>();
			boolean hasValue = false;
			Class<?> cls = obj.getClass();
			Field[] fields = cls.getDeclaredFields();
			result.append("UPDATE " + cls.getSimpleName() + " SET ");
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) { // 忽略静态成员
					String fieldName = field.getName();
					Object value = cls.getMethod(getterName(fieldName)).invoke(obj);
					if (field.getAnnotation(ID.class) == null) {
						if (value != null) {
							result.append(fieldName + " = '" + value + "', ");
							hasValue = true;
						}
					} else
						ids.put(fieldName, value);
				}
			}
			if (!hasValue)
				throw new Exception("No data to update");
			result.setLength(result.length() - 2);
			result.append(" WHERE ");
			for (Map.Entry<String, Object> map : ids.entrySet())
				result.append(map.getKey() + " = '" + map.getValue() + "' AND ");
			result.setLength(result.length() - 5);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成删除的SQL语句
	 * @param obj 实体对象
	 * @return SQL语句
	 */
	public static String delete(final Object obj) {
		try {
			StringBuilder result = new StringBuilder();
			boolean hasID = false;
			Class<?> cls = obj.getClass();
			Field[] fields = cls.getDeclaredFields();
			result.append("DELETE FROM " + cls.getSimpleName() + " WHERE ");
			for (Field field : fields) {
				if (field.getAnnotation(ID.class) != null) {
					String fieldName = field.getName();
					Object value = cls.getMethod(getterName(fieldName)).invoke(obj);
					if (value != null) {
						result.append(field.getName() + " = '" + value + "' AND ");
						hasID = true;
					}
				}
			}
			if (!hasID)
				throw new Exception("No data to delete");
			result.setLength(result.length() - 5);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成删除的SQL语句
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return SQL语句
	 */
	public static String delete(final Class<?> cls, final String condition) {
		try {
			StringBuilder result = new StringBuilder();
			result.append("DELETE FROM " + cls.getSimpleName() + " WHERE ");
			if (!(condition == null || "".equals(condition)))
				result.append(condition);
			else
				throw new Exception("No data to delete");
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成查询的SQL语句
	 * @param cls 实体类对象
	 * @return SQL语句
	 */
	public static String load(final Class<?> cls) {
		return load(cls, null);
	}
	
	/**
	 * 生成查询的SQL语句
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return SQL语句
	 */
	public static String load(final Class<?> cls, final String condition) {
		try {
			Field[] fields = cls.getDeclaredFields();
			if (fields.length == 0)
				throw new Exception("No data to load");
			StringBuilder result = new StringBuilder();
			result.append("SELECT ");
			
			/*for (Field field : fields)
				result.append(field.getName() + ", ");
			result.setLength(result.length() - 2);*/
			
			// 之前用*代替上面被注释掉的代码，查询所有字段，效果相同，但代码运行效率高些，无需循环
			result.append("*");
			
			result.append(" FROM " + cls.getSimpleName());
			if (!(condition == null || "".equals(condition)))
				result.append(" WHERE " + condition);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成查询特定字段的SQL语句
	 * @param params 参数数组
	 * @param cls 实体类对象
	 * @return SQL语句
	 */
	public static String load(final String[] params, final Class<?> cls) {
		return load(params, cls, null);
	}
	
	/**
	 * 生成查询特定字段的SQL语句
	 * @param params 参数数组
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return SQL语句
	 */
	public static String load(final String[] params, final Class<?> cls, final String condition) {
		try {
			if (params == null || params.length == 0)
//				throw new Exception("No data to load");
				return load(cls, condition); // 若没有指定字段则查询所有字段
			StringBuilder result = new StringBuilder();
			result.append("SELECT ");
			
			for (String param : params)
				result.append(param + ", ");
			
			result.setLength(result.length() - 2);
			result.append(" FROM " + cls.getSimpleName());
			if (!(condition == null || "".equals(condition)))
				result.append(" WHERE " + condition);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成查询数目的SQL语句
	 * @param cls 实体类对象
	 * @return SQL条件
	 */
	public static String count(final Class<?> cls) {
		return count(cls, null);
	}
	
	/**
	 * 生成查询数目的SQL语句
	 * @param cls 实体类对象
	 * @param condition 条件
	 * @return SQL语句
	 */
	public static String count(final Class<?> cls, final String condition) {
		try {
			Field[] fields = cls.getDeclaredFields();
			if (fields.length == 0)
				throw new Exception("No data to load");
			StringBuilder result = new StringBuilder();
			result.append("SELECT COUNT(*) FROM " + cls.getSimpleName());
			if (!(condition == null || "".equals(condition)))
				result.append(" WHERE " + condition);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取getter方法名
	 * @param fieldName 成员名
	 * @return 方法名
	 */
	public static String getterName(final String fieldName) {
		StringBuilder result = new StringBuilder("get" + fieldName);
		result.setCharAt(3, Character.toUpperCase(fieldName.charAt(0)));
		return result.toString();
	}
	
	/**
	 * 获取setter方法名
	 * @param fieldName 成员名
	 * @return 方法名
	 */
	public static String setterName(final String fieldName) {
		StringBuilder result = new StringBuilder("set" + fieldName);
		result.setCharAt(3, Character.toUpperCase(fieldName.charAt(0)));
		return result.toString();
	}
}
