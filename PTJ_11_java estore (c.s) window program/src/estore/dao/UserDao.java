package estore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estore.database.DBInfo;
import estore.entity.User;

public class UserDao extends DBInfo {
	/**
	 * @description Check user whether exist when login
	 * @param name
	 * @return boolean exist
	 * @author XLT
	 */
	public synchronized boolean checkUser(String name, String password) {
		this.connectJdbc();
		String sql = "select * from user where name = ? and password = ?;";
		boolean exist = false;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			exist = preparedStatement.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return exist;
	}

	public synchronized boolean checkUserName(String name) {
		this.connectJdbc();
		String sql = "select * from user where name = ?;";
		boolean exist = false;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next())
				exist = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return exist;
	}

	public synchronized User getUserByName(String name) {
		this.connectJdbc();
		String sql = "select * from user where name = ?;";
		User u = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			u = new User();
			u.setId(rs.getInt("id"));
			u.setName(rs.getString("name"));
			u.setPassword(rs.getString("password"));
			u.setRole(rs.getInt("role"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return u;
	}

	public synchronized boolean delUserByName(String name) {
		this.connectJdbc();
		String sql = "delete from user where name = ?;";
		boolean exist = false;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			exist = preparedStatement.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return exist;
	}

	public synchronized void addUser(User u) {
		this.connectJdbc();

		String sql = "insert into user(" + "name," + "password," + "email,"
				+ "address," + "phone," + "credit," + "role)"
				+ "values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, u.getName());
			preparedStatement.setString(2, u.getPassword());
			preparedStatement.setString(3, u.getEmail());
			preparedStatement.setString(4, u.getAddress());
			preparedStatement.setString(5, u.getPhone());
			preparedStatement.setString(6, u.getCredit());
			preparedStatement.setInt(7, 0);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
	}
}
