package net.jeth.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

	private String url;
	private String user;
	private String password;

	private static DatabaseManager instance;

	private DatabaseManager() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://202.117.16.188:3306/search_engine?useUnicode=true&characterEncoding=UTF-8";
		user = "root";
		password = "root";
	}

	public static DatabaseManager getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance= new DatabaseManager();
		}
		return instance;
	}

	public void update(String sql, Object... objects) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				preparedStatement.setObject(i + 1, objects[i]);
			}
			preparedStatement.executeUpdate();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	public List<Map<String, Object>> query(String sql, Object... objects) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				preparedStatement.setObject(i + 1, objects[i]);
			}
			resultSet = preparedStatement.executeQuery();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
					map.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
				}
				list.add(map);
			}
			return list;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} finally {
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
					}
				} finally {
					if (connection != null) {
						connection.close();
					}
				}
			}
		}
	}
}
