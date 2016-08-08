package shop.eventImp;

import shop.event.Event;
import shop.event.EventType;

public class OrderCanceledByCreatorEvent extends Event{
	public String creaotrID;
	public String orderID;
	public static enum Type implements EventType {
    	/**
    	 * 事件类型标识：订单被组长取消
    	 */
       Order_Canceled_By_Creator
    }
	public OrderCanceledByCreatorEvent(String creatorID, String orderID) {
		this.creaotrID = creatorID;
		this.orderID = orderID;
	}
}
