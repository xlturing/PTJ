package estore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estore.database.DBInfo;
import estore.entity.Copy;
import estore.entity.Rent;

public class RentDao extends DBInfo {
	public synchronized void addCopy(Rent r) {
		this.connectJdbc();

		String sql = "insert into rent(" + "user_id," + "copy_id,"
				+ "rent_date," + "due_date) "
				+ "values(?,?,now(),date_add(now(),interval 1 month))";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, r.getUser_id());
			preparedStatement.setInt(2, r.getCopy_id());
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

	// return rent records by user_id
	public synchronized List<Rent> getRent(int user_id) {
		List<Rent> lr = new ArrayList<Rent>();
		String sql;
		this.connectJdbc();
		sql = "select * from rent where user_id = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, user_id);
			ResultSet rs = preparedStatement.executeQuery();
			Rent r;
			while (rs.next()) {
				r = new Rent();
				r.setId(rs.getInt("id"));
				r.setCopy_id(rs.getInt("copy_id"));
				r.setUser_id(rs.getInt("user_id"));
				r.setDue_date(rs.getString("due_date"));
				r.setRent_date(rs.getString("rent_date"));
				r.setLate_fee(rs.getDouble("late_fee"));
				lr.add(r);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return lr;
	}

	// overload method return all rent records
	public synchronized List<Rent> getRent() {
		List<Rent> lr = new ArrayList<Rent>();
		String sql;
		this.connectJdbc();
		sql = "select * from rent;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			Rent r;
			while (rs.next()) {
				r = new Rent();
				r.setId(rs.getInt("id"));
				r.setCopy_id(rs.getInt("copy_id"));
				r.setUser_id(rs.getInt("user_id"));
				r.setDue_date(rs.getString("due_date"));
				r.setRent_date(rs.getString("rent_date"));
				r.setLate_fee(rs.getDouble("late_fee"));
				lr.add(r);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return lr;
	}

	// update return date
	public synchronized void returnRent(int rent_id) {
		this.connectJdbc();
		String sql = "update rent set return_date = now() where id = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, rent_id);
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

	// set late fee
	public synchronized void setLateFee(int id, double late_fee) {
		this.connectJdbc();
		String sql = "update rent set late_fee = ? where id = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDouble(1, late_fee);
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
}
