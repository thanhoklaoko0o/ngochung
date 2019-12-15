package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testjdbc {
	public Connection cn;
	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String databaseURL = "jdbc:mysql://localhost:3306/estate2019";
			String user = "root";
			String password = "sa123456";
			return DriverManager.getConnection(databaseURL, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		conn=getConnection();
		String sql="Update building set name=?,ward=?,street=? where id=?";
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, "Tòa Nhà Cntt");
			statement.setString(2, "Xuân Hòa");
			statement.setString(3, "Gio Linh");
			statement.setInt(4, 4);
			
			int kq = statement.executeUpdate();
			if(kq>0) {
				System.out.println("ok");
			}else {
				System.out.println("faile");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
