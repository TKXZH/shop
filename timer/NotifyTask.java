package shop.timer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;

import shop.db.Init;
import shop.filter.NotifyTeamOwnerRules;
/**
 *
 * @author xvzh
 *定时任务模块，定时发送通知提醒组长
 */
public class NotifyTask extends TimerTask{
	
	private String message;
	public NotifyTask(String message) {
		this.message = message;
	}
	@Override
	public void run() {
		try {
			ArrayList<String> creators = Init.getAllTeamCreators();
			NotifyTeamOwnerRules ntor = NotifyTeamOwnerRules.getInstence(message);
			for(String creator : creators) {
				ntor.addRules(creator);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
