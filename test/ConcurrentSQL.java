package shop.test;

import java.sql.SQLException;

public class ConcurrentSQL implements Runnable{
	public ConcurrentSQL() {
		
	}
	@Override
	public void run() {
		try {
			while(true) {
				ConnAndStamt.getStatement().execute("update concurrent set id = 2 where id = 3");
				ConnAndStamt.getStatement().execute("update concurrent set id = 3 where id = 2");
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		Thread.yield();
	}
	
	
}
