package shop.filter;

import java.util.Map;

public interface Rules {
	/**
	 * 判断请求是否属于被本过滤器拦截  
	 * @param userID  用户访问userID
	 * 
	 */
	public boolean isMatch(String userID);
	
	/**
	 * 请求拦截处理结束，移除
	 * @param userID
	 */
	void removeUser(String userID);
	
	/**
	 * 处理拦截下的请求
	 * @param userID
	 * @param map  结果集
	 * @throws Exception
	 */
	void process(String userID, Map<String, Object> map) throws Exception;
	
	/**
	 * 添加拦截规则
	 * @param userID
	 * @param tid
	 */
	public void addRules(String userID, String tid);
}
