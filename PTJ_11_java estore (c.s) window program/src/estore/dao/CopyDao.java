package estore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estore.database.DBInfo;
import estore.entity.Copy;
import estore.entity.User;

public class CopyDao extends DBInfo {
	// get copy by title
	public synchronized List<Copy> getCopy(String title) {
		List<Copy> lc = new ArrayList<Copy>();
		String sql;
		this.connectJdbc();
		if (title.trim().equals(""))
			sql = "select * from copy;";
		else
			sql = "select * from copy where title like(?);";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			if (!title.trim().equals(""))
				preparedStatement.setString(1, title);
			ResultSet rs = preparedStatement.executeQuery();
			Copy c;
			while (rs.next()) {
				c = new Copy();
				c.setId(rs.getInt("id"));
				c.setStore_id(rs.getInt("store_id"));
				c.setTitle(rs.getString("title"));
				c.setType(rs.getInt("type"));
				c.setFee(rs.getDouble("fee"));
				c.setDescription(rs.getString("description"));
				c.setStatus(rs.getInt("status"));
				lc.add(c);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return lc;
	}

	public synchronized void addCopy(Copy c) {
		this.connectJdbc();

		String sql = "insert into copy(" + "store_id," + "title," + "type,"
				+ "fee," + "description," + "status) " + "values(?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, c.getStore_id());
			preparedStatement.setString(2, c.getTitle());
			preparedStatement.setInt(3, c.getType());
			preparedStatement.setDouble(4, c.getFee());
			preparedStatement.setString(5, c.getDescription());
			preparedStatement.setInt(6, c.getStatus());
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

	public synchronized boolean delCopy(int id) {
		this.connectJdbc();
		String sql = "delete from copy where id = ?;";
		boolean b = false;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			b = preparedStatement.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return b;
	}

	// update status
	public synchronized void updateStatus(int id, int status) {
		this.connectJdbc();
		String sql = "update copy set status = ? where id = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, status);
			preparedStatement.setInt(2, id);
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

	/**
	 * @description check this copy status: emtyp/on hold
	 * @param id
	 * @return
	 * @author XLT
	 */
	public synchronized boolean checkStatus(int id) {
		this.connectJdbc();
		String sql = "select * from copy where id = ?;";
		boolean empty = false;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			if (rs.getInt("status") == 0)
				empty = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return empty;
	}
}
