package shop.filter;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author xvzh
 *过滤器，负责Notify接口的请求过滤
 */
public class DoFilter {
	private static DoFilter doFilter;
	private ArrayList<Rules> rules;
	private DoFilter() {
		
	}
	
	public static DoFilter getInstence() {
		if(doFilter ==  null) {
			doFilter = new DoFilter();
		}
		return doFilter;
	}
	
	public void addRules(Rules rule) {
		if(rules == null) {
			rules = new ArrayList<Rules>();
		}
		rules.add(rule);
	}
	
	public void doFilter(String userID, Map<String,Object> map) throws Exception {
		if(rules != null) {
			for(Rules rule : rules) {
				if(rule.isMatch(userID)) {
					rule.process(userID, map);
					rule.removeUser(userID);
				}
			}
		}
		
		if(map.size() != 0) {
			map.put("stat", "alive");
		}
	}
}
