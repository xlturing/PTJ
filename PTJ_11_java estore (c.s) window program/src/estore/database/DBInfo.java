package estore.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

public class DBInfo {
	// load mysql driver
	protected String driver = "com.mysql.jdbc.Driver";
	// you should replace your user and password which is your mysql setting
	// and I create a database named PTJ_10, you should change it to your own
	protected String url = "jdbc:mysql://127.0.0.1:3306/PTJ_11?useUnicode=true&characterEncoding=UTF-8";
	protected String user = "root";
	protected String password = "xlt7057977";
	protected Connection conn;

	protected String tableName = "";
	// int modifyCount = 0;
	double preTime = 0;
	double interval = 5 * 60 * 1000;

	public DBInfo() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void connectJdbc() {
		if (conn == null) {
			System.out.println("connecting....");
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(true);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void closeConnection() {
		Date d = new Date();
		double now = d.getTime();
		if (now - preTime > this.interval) {
			try {
				if (conn != null) {
					System.out.println(now + "\t" + preTime);
					System.out.println("disconnecting....");
					conn.close();
					conn = null;
					preTime = now;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}