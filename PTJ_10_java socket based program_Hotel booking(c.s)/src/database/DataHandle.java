package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Book;
import entity.Hotel;

public class DataHandle extends DBInfo {
	public void getConnection() {}

	// User book room
	public synchronized void addBooking(Book b) {
		this.connectJdbc();

		String sql = "insert into book(" + "id," + "user_name," + "hotel_name,"
				+ "city," + "in_date," + "out_date) " + "values(?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, b.getId());
			preparedStatement.setString(2, b.getUser_name());
			preparedStatement.setString(3, b.getHotel_name());
			preparedStatement.setString(4, b.getCity());
			preparedStatement.setString(5, b.getIn_date());
			preparedStatement.setString(6, b.getOut_date());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("Insert a book record:" + b.getUser_name()
					+ " book " + b.getHotel_name() + " in " + b.getCity());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
	}

	// Server add hotel
	public synchronized void addHotel(Hotel h) {
		this.connectJdbc();

		String sql = "insert into hotel(" + "id," + "name," + "city," + "rate,"
				+ "rooms) " + "values(?,?,?,?,?);";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, h.getId());
			preparedStatement.setString(2, h.getName());
			preparedStatement.setString(3, h.getCity());
			preparedStatement.setDouble(4, h.getRate());
			preparedStatement.setInt(5, h.getRooms());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("Insert a hotel record:" + h.getName() + " in "
					+ h.getCity() + " have " + h.getRooms() + " rooms "
					+ " and room rate is" + h.getRate());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
	}

	// get all city
	public synchronized List<String> getCityList() {
		List<String> cityList = new ArrayList<String>();
		this.connectJdbc();
		String sql = "select distinct city from hotel;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				cityList.add(rs.getString("city"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return cityList;
	}

	// get all hotel by city and it will get the hotel remain rooms by check-in
	// and check-out dates
	public synchronized List<Hotel> getHotelList(String inDate, String outDate,
			String city) {
		List<Hotel> hotelList = new ArrayList<Hotel>();
		this.connectJdbc();
		String sql = "select * from hotel where city = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, city);
			ResultSet rs = preparedStatement.executeQuery();
			Hotel h;
			while (rs.next()) {
				h = new Hotel();
				h.setId(rs.getInt("id"));
				h.setName(rs.getString("name"));
				h.setCity(rs.getString("city"));
				h.setRate(rs.getDouble("rate"));
				sql = "SELECT count(*) as count from book where  hotel_name = ? and city = ? and (in_date BETWEEN ? AND ? or out_date BETWEEN ? AND ?);";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, h.getName());
				preparedStatement.setString(2, h.getCity());
				preparedStatement.setString(3, inDate);
				preparedStatement.setString(4, outDate);
				preparedStatement.setString(5, inDate);
				preparedStatement.setString(6, outDate);
				ResultSet rs1 = preparedStatement.executeQuery();
				rs1.next();
				h.setRooms(rs.getInt("rooms") - rs1.getInt("count"));
				hotelList.add(h);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return hotelList;
	}

	// get a hotel by id and get its vacant rooms
	public synchronized Hotel getHotel(String inDate, String outDate, int id) {
		Hotel h = new Hotel();
		this.connectJdbc();
		String sql = "select * from hotel where id = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			h.setId(rs.getInt("id"));
			h.setName(rs.getString("name"));
			h.setCity(rs.getString("city"));
			h.setRate(rs.getDouble("rate"));
			sql = "SELECT count(*) as count from book where  hotel_name = ? and city = ? and (in_date BETWEEN ? AND ? or out_date BETWEEN ? AND ?);";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, h.getName());
			preparedStatement.setString(2, h.getCity());
			preparedStatement.setString(3, inDate);
			preparedStatement.setString(4, outDate);
			preparedStatement.setString(5, inDate);
			preparedStatement.setString(6, outDate);
			ResultSet rs1 = preparedStatement.executeQuery();
			rs1.next();
			h.setRooms(rs.getInt("rooms") - rs1.getInt("count"));

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return h;
	}
}
