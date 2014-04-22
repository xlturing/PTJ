package estore.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import estore.database.DBInfo;
import estore.entity.Log;
import estore.entity.Rent;

public class LogDao extends DBInfo {
	public synchronized void addLog(Log l) {
		this.connectJdbc();

		String sql = "insert into log(" + "store_id," + "user_id,"
				+ "copy_id) " + "values(?,?,?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, l.getStore_id());
			preparedStatement.setInt(2, l.getUser_id());
			preparedStatement.setInt(3, l.getCopy_id());
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
