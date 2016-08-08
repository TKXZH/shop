package shop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Init {

    private static Statement statement;
    public static Statement getStatement() throws ClassNotFoundException, SQLException{
        if(statement != null) {
        }   else {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8","root", "941031");
            statement = connection.createStatement();
        }

//        statement.execute("create table user(id int primary key not null auto_increment,name varchar(50),tel varchar(20))");
        return statement;
    }
    public static ArrayList<String> getAllTeamCreators() throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select distinct creatorID from team");
		ArrayList<String> creators = new ArrayList<String>();
		while(res.next()) {
			creators.add(res.getString("creatorID"));
		}
		return creators;
    }
    public static boolean userExist(String userName, String tel) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from user where name = '" + userName + "' and tel = '" + tel + "';");
    	if(res.next()) {
    		return true;
    	}
    	return false;
    }
    public static String getUserID(String userName, String tel) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from user where name = '" + userName + "' and tel = '" + tel + "';");
    	String ID;
    	res.next();
    	ID = res.getString("id");
    	return ID;
    }
    public static void createUser(String id, String username, String tel) throws SQLException, ClassNotFoundException{
        getStatement().execute("insert into user values ( '"+ id + "','" + username + "','" + tel + "');");
    }
    
    public static boolean authUser(String id) throws SQLException, ClassNotFoundException{
    	ResultSet res = getStatement().executeQuery("select * from user where id='" + id + "';");
    	if(res.next()) {
    		return true;
    	}	else {
			return false;
		}
    }
    /**
     * 调用此方法需保证合法的ID
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getUsernameByID(String id) throws ClassNotFoundException, SQLException {
    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8","root", "941031");
    	ResultSet res = connection.createStatement().executeQuery("select * from user where id='" + id + "';");
    	res.next();
    	String name = res.getString("name");
    	res.close();
    	connection.close();
    	return name;
    }
    public static String getUsertelByID(String id) throws ClassNotFoundException, SQLException {
    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8","root", "941031");
    	ResultSet res = connection.createStatement().executeQuery("select * from user where id='" + id + "';");
    	res.next();
    	String tel = res.getString("tel");
    	res.close();
    	connection.close();
    	return tel;
    }
    public static String getProductNameByID(String id) throws ClassNotFoundException, SQLException {
    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8","root", "941031");
    	ResultSet res = connection.createStatement().executeQuery("select * from product where id='" + id + "';");
    	res.next();
    	String name = res.getString("name");
    	res.close();
    	connection.close();
    	return name;
    }
    public static String getProductPriceByID(String id) throws ClassNotFoundException, SQLException {
    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8","root", "941031");
    	ResultSet res = connection.createStatement().executeQuery("select * from product where id='" + id + "';");
    	res.next();
    	String price = res.getString("price");
    	res.close();
    	connection.close();
    	return price;
    }
    public static HashMap<String,ArrayList<Map<String, String>>> getProducts() throws SQLException,ClassNotFoundException{
    	ResultSet res = getStatement().executeQuery("select * from product");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		map.put("id", res.getString("id"));
    		map.put("name", res.getString("name"));
    		map.put("price", res.getString("price"));
    		list.add(map);
    	}
    	HashMap<String,ArrayList<Map<String, String>>> map = new HashMap<String,ArrayList<Map<String, String>>>();
    	map.put("products", list);
    	return map;
    }
    
    public static boolean authProduct(String productID) throws Exception {
    	ResultSet res = getStatement().executeQuery("select * from product where id = '" + productID + "';");
    	if(res.next()) {
    		return true;
    	}	else {
			return false;
		}
    }
    
    public static boolean teamExist(String tid) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from team where tid = '" + tid + "';");
    	if(res.next()) {
    		return true;
    	}	else {
			return false;
		}
    }
    
    public static void createOrder(String productID, String userID, int productNum,int tid,String time) throws ClassNotFoundException, SQLException {

    	getStatement().execute("insert into list(productID,userID,productNum,orderStatus,tid,time) values (" + productID + ",'" + userID + "'," + productNum + ",0,"+tid+"," + time + ");");
    }
    
    public static void createTeam(String creatorID,String teamName) throws ClassNotFoundException, SQLException {
    	getStatement().execute("insert into team values(null,'" + creatorID + "','" + teamName + "');");
    	ResultSet res = getStatement().executeQuery("select last_insert_id(); ");
    	res.next();
    	int teamID = res.getInt(1);
    	getStatement().execute("insert into team2user values (null ,'" + teamID + "','" + creatorID + "');");
    }
    
    public static HashMap<String,ArrayList<Map<String,String>>> getTeams() throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from team");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		map.put("teamID", res.getString("tid"));
    		map.put("teamName", res.getString("tname"));
    		String creatorID = res.getString("creatorID");
    		map.put("creatorID", creatorID);
    		map.put("creatorName", getUsernameByID(creatorID));
    		map.put("creatorTEL", getUsertelByID(creatorID));
    		list.add(map);	
    	}
    	HashMap<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map.put("teams", list);
    	return map;
    }
    public static void deleteUserFromTeam(String tid, String userID) throws ClassNotFoundException, SQLException {
    	getStatement().execute("delete from team2user where tid = " + tid +" and userID = '" + userID + "';");
    	getStatement().execute("update list set orderStatus = 2 where userID = '" + userID + "' and tid =" + tid +" and orderStatus = 0;");
    }
    public static void joinTeam(String userID, String teamID) throws ClassNotFoundException, SQLException {
    	getStatement().execute("insert into team2user values (null ,'" + teamID + "','" + userID + "');");
    }
    
    public static boolean alreadyBelongsToThisTeam(String userID, String teamID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from team2user where userID = '" + userID +"' and tid = '" + teamID + "';" );
    	if(res.next()) {
    		return true;
    	}	else {
			return false;
		}
    }
    public static Map<String,ArrayList<Map<String,String>>> getMyTeams(String userID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from team where tid = any(select tid from team2user where userID='" + userID + "');");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		String creatorID = res.getString("creatorID");
    		map.put("tid", res.getString("tid"));
    		map.put("creatorID", creatorID);
    		map.put("creatorTEL", getUsertelByID(creatorID));
    		map.put("creatorName",getUsernameByID(creatorID));
    		map.put("teamName", res.getString("tname"));
    		list.add(map);
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map.put("teams", list);
    	return map;
    }
    public static Map<String,ArrayList<Map<String,String>>> getTeamOrders(String tid) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from list where tid = " + tid + " and orderStatus = 0;" );
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		String productID = res.getString("productID");
    		map.put("productID", productID);
    		map.put("productNum", res.getString("productNum"));
    		map.put("productName", getProductNameByID(productID));
    		map.put("priductPrice", getProductPriceByID(productID));
    		String userID = res.getString("userID");
    		map.put("userID", userID);
    		map.put("userName", getUsernameByID(userID));
    		map.put("userTEL", getUsertelByID(userID));
    		map.put("orderID", res.getString("id"));
    		map.put("orderTime", res.getString("time"));
    		list.add(map);
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map.put("teams", list);
    	return map;
    }
    
    public static boolean isCreator(String userID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from team where creatorID = '" + userID + "';");
    	if(res.next()) {
    		return true;
    	}	else {
			return false;
		}
    }
    
    public static Map<String,ArrayList<Map<String,String>>> getTeamPropertyByCreator(String creatorID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from team where creatorID = '" + creatorID + "';");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		map.put("tid", res.getString("tid"));
    		map.put("creatorID", res.getString("creatorID"));
    		map.put("teamName", res.getString("tname"));
    		list.add(map);
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map.put("teamsBelongsToME", list);
    	return map;
    }
    public static ArrayList<Map<String,String>> getTeamInfoByTid(String tid) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from team where tid = '" + tid + "';");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		map.put("tid", res.getString("tid"));
    		map.put("creatorID", res.getString("creatorID"));
    		map.put("teamName", res.getString("tname"));
    		list.add(map);
    	}
    	return list;
    }
    public static void deleteTeam(String tid) throws ClassNotFoundException, SQLException {
    	getStatement().execute("delete from team2user where tid = " + tid +";");
    	getStatement().execute("delete from team where tid = " + tid + ";");
    	getStatement().execute("update list set orderStatus = 2 where tid = " + tid + " and orderStatus = 0;");
    }
    public static Map<String,ArrayList<Map<String,String>>> getTeamUsers(String tid) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from team2user where tid = " + tid + ";");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		String userID = res.getString("userID");
    		map.put("userID",userID);
    		map.put("userTEL", getUsertelByID(userID));
    		map.put("userName", getUsernameByID(userID));
    		list.add(map);
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map.put("usersInformationBelongsToThisTeam", list);
    	return map;
    }
    public static ArrayList<String> getTeamUserID(String tid) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from team2user where tid = " + tid + ";");
    	ArrayList<String> list = new ArrayList<String>();
    	while(res.next()) {
    		String userID = res.getString("userID");
    		list.add(userID);
    	}
    	return list;
    }
    public static void clearAllMyHistoryOrders(String userID) throws ClassNotFoundException, SQLException {
    	getStatement().execute("delete from list where orderStatus = 1 or orderStatus = 2 and userID = '" + userID + "';");
    }
    public static Map<String,ArrayList<Map<String,String>>> getMyOrders(String userID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from list where userID = '" + userID + "';");
    	ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	while(res.next()) {
    		Map<String,String> map = new HashMap<String,String>();
    		String productID = res.getString("productID");
    		map.put("productID", productID);
    		map.put("productNum", res.getString("productNum"));
    		map.put("productName", getProductNameByID(productID));
    		map.put("productPrice", getProductPriceByID(productID));
    		map.put("orderStatus", res.getString("orderStatus"));
    		map.put("belongsToTeam", res.getString("tid"));
    		map.put("orderID", res.getString("id"));
    		map.put("orderTime", res.getString("time"));
    		list.add(map);
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map.put("orders", list);
    	return map;
    }
    public static HashMap<String, String> getOrderInfoByOrderID(String orderID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from list where id = " + orderID + ";");
    	if(res.next()) {
    		HashMap<String, String> info = new HashMap<String, String>();
    		info.put("orderID", res.getString("id"));
    		info.put("productID", res.getString("productID"));
    		info.put("userID", res.getString("userID"));
    		info.put("productNum", res.getString("productNum"));
    		info.put("tid", res.getString("tid"));
    		info.put("time", res.getString("time"));
    		return info;
    	}	else {
			return null;
		}
    }
    public static void cancelOrder(String id) throws ClassNotFoundException, SQLException {
    	getStatement().execute("update list set orderStatus = 2 where id = " + id + " ;");
    }
    public static Map<String,String> getProductInformation(String productID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery(" select * from product where id = '" + productID + "';");
    	Map<String,String> map = new HashMap<String, String>();
    	while(res.next()) {
    		map.put("productID",res.getString("id"));
    		map.put("productName", res.getString("name"));
    		map.put("price", res.getString("price"));
    	}
    	return map;
    }
    
    public static void finishTeamOrder(String tid) throws ClassNotFoundException, SQLException {
    	getStatement().execute("update list set orderStatus = 1 where tid = " + tid + " and orderStatus = 0;");
    }
    
    public static boolean isInTeam(String userID) throws ClassNotFoundException, SQLException {
    	ResultSet res = getStatement().executeQuery("select * from team2user where userID='" + userID + "';");
    	if(res.next()) {
    		return true;
    	}	else {
			return false;
		}
    }
}
