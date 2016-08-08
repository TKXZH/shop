package shop.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shop.db.Init;

public class OrderCanceledByCreatorRules implements Rules{
	private HashMap<String, ArrayList<String>> canceledOrders = new HashMap<String, ArrayList<String>>();
	private static OrderCanceledByCreatorRules orderCanceledByCreatorRules;
	private OrderCanceledByCreatorRules() {
		
	}
	public static OrderCanceledByCreatorRules getInstence() {
		if(orderCanceledByCreatorRules == null) {
			orderCanceledByCreatorRules = new OrderCanceledByCreatorRules();
			DoFilter.getInstence().addRules(orderCanceledByCreatorRules);
		}
		return orderCanceledByCreatorRules;
	}
	@Override
	public boolean isMatch(String userID) {
		if(canceledOrders.get(userID) != null && canceledOrders.get(userID).size() != 0 ) {
			return true;
		}	else {
			return false;
		}
	}

	@Override
	public void removeUser(String userID) {
		canceledOrders.remove(userID);
	}

	@Override
	public void process(String userID, Map<String, Object> map)
			throws Exception {
		ArrayList<String> orders = canceledOrders.get(userID);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		for(String orderID : orders) {
			HashMap<String, String> order = Init.getOrderInfoByOrderID(orderID);
			list.add(order);
		}
		map.put("your order is canceled", list);	
	}

	@Override
	public void addRules(String userID, String orderID) {
		if(canceledOrders.get(userID) == null) {
			ArrayList<String> orders = new ArrayList<String>();
			orders.add(orderID);
			canceledOrders.put(userID, orders);
		}	else {
			ArrayList<String> orders = canceledOrders.get(userID);
			orders.add(orderID);
		}
		
	}

}
