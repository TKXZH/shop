package shop.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SQL {
	public static void main(String args[]) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://115.159.202.100:3306/test","admin", "admin");
        Statement statement  = connection.createStatement();
        ResultSet res = statement.executeQuery("select name from user");
        while(res.next()) {
        	System.out.println(res.getString("name"));
        }
	}
}
