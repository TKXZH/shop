package shop.filter;

import java.util.ArrayList;
import java.util.Map;

public class NotifyTeamOwnerRules implements Rules{
	private ArrayList<String> creators = new ArrayList<String>();
	public String message = "";
	private static NotifyTeamOwnerRules notifyTeamOwnerRules;
	public static NotifyTeamOwnerRules getInstence(String message) {
		if(notifyTeamOwnerRules == null) {
			notifyTeamOwnerRules = new NotifyTeamOwnerRules(message);
			DoFilter.getInstence().addRules(notifyTeamOwnerRules);
		}
		notifyTeamOwnerRules.message = message;
		return notifyTeamOwnerRules;
	}
	private NotifyTeamOwnerRules(String message) {
		this.message = message;
	}
	@Override
	public boolean isMatch(String userID) {
		if(creators != null && creators.size() != 0 && creators.contains(userID)) {
			return true;
		}	else {
			return false;
		}
	}

	@Override
	public void removeUser(String userID) {
		creators.remove(userID);
	}

	@Override
	public void process(String userID, Map<String, Object> map)
			throws Exception {
		map.put("msg2creator", this.message);	
	}

	@Override
	public void addRules(String userID, String tid) {}
	public void addRules(String userID) {
		creators.add(userID);
	}
}
