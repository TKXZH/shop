package shop.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shop.db.Init;

public class NotifyMealRules implements Rules{
	private static NotifyMealRules notifyMealRules;
	private NotifyMealRules() {
		
	}
	private HashMap<String, ArrayList<String>> MealNotifies = new HashMap<String, ArrayList<String>>();
	public static NotifyMealRules getInstence() {
		if(notifyMealRules == null) {
			notifyMealRules = new NotifyMealRules();
			DoFilter.getInstence().addRules(notifyMealRules);
		}
		return notifyMealRules;
	}
	@Override
	public boolean isMatch(String userID) {
		if(MealNotifies.get(userID) != null && MealNotifies.get(userID).size() != 0 ) {
			return true;
		}	else {
			return false;
		}
	}

	@Override
	public void removeUser(String userID) {
		MealNotifies.remove(userID);
	}

	@Override
	public void process(String userID, Map<String, Object> map)
			throws Exception {
		ArrayList<String> tids = MealNotifies.get(userID);
		ArrayList<ArrayList<Map<String,String>>> list = new ArrayList<ArrayList<Map<String,String>>>();
		for(String tid : tids) {
			list.add(Init.getTeamInfoByTid(tid));
		}
		map.put("you are notified by", list);
		
	}
	@Override
	public void addRules(String userID, String tid) {
		if(MealNotifies.get(userID) == null) {
			ArrayList<String> tids = new ArrayList<String>();
			tids.add(tid);
			MealNotifies.put(userID, tids);
		}	else {
			ArrayList<String> tids = MealNotifies.get(userID);
			tids.add(tid);
		}
		
	}
	
}
