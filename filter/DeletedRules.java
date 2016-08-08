package shop.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shop.db.Init;

public class DeletedRules implements Rules{
	public static DeletedRules deletedRules;
	private HashMap<String, ArrayList<String>> deletedUsers = new HashMap<String, ArrayList<String>>();
	private DeletedRules() {
		
	}
	public static DeletedRules getInstence() {
		if(deletedRules == null) {
			deletedRules = new DeletedRules();
			DoFilter.getInstence().addRules(deletedRules);
		}
		return deletedRules;
	}
	
	public void addRules(String userID, String tid) {
		if(deletedUsers.get(userID) == null) {
			ArrayList<String> tids = new ArrayList<String>();
			tids.add(tid);
			deletedUsers.put(userID, tids);
		}	else {
			ArrayList<String> tids = deletedUsers.get(userID);
			tids.add(tid);
		}
	}
	
	/**
	 * 判断是否符合过滤规则
	 */
	public boolean isMatch(String userID) {
		if(deletedUsers.get(userID) != null && deletedUsers.get(userID).size() != 0 ) {
			return true;
		}	else {
			return false;
		}
	}
	@Override
	public void removeUser(String userID) {
		deletedUsers.remove(userID);
	}
	@Override
	public void process(String userID, Map<String, Object> map) throws Exception {
		ArrayList<String> tids = deletedUsers.get(userID);
		ArrayList<ArrayList<Map<String,String>>> list = new ArrayList<ArrayList<Map<String,String>>>();
		for(String tid : tids) {
			list.add(Init.getTeamInfoByTid(tid));
		}
		map.put("deleted by team", list);
	}


}
