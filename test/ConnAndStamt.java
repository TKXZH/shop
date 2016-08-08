package shop.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnAndStamt {
	private static Connection connection;
	private static Statement statement;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8","root", "941031");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Statement getStatement() {
		return statement;
	}
}
