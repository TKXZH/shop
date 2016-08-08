package shop.eventImp;

import java.sql.SQLException;

import shop.db.Init;
import shop.event.Event;
import shop.event.EventType;
import shop.event.Listener;
import shop.filter.OrderCanceledByCreatorRules;

public class OrderCanceledByCreatorListener implements Listener{

	@Override
	public void onEvent(EventType type, Event e) throws ClassNotFoundException, SQLException {
		OrderCanceledByCreatorEvent event = (OrderCanceledByCreatorEvent)e;
		String orderID = event.orderID;
		String userID;
		if(Init.getOrderInfoByOrderID(orderID) != null); {
			userID = Init.getOrderInfoByOrderID(orderID).get("userID");
			OrderCanceledByCreatorRules.getInstence().addRules(userID, orderID);
		}
	}

}
