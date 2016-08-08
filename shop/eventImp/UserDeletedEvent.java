package shop.eventImp;


import shop.event.Event;
import shop.event.EventType;


public class UserDeletedEvent extends Event {
	
	public static enum Type implements EventType {
    	/**
    	 * 事件类型标识：用户被组长删除
    	 */
       After_Delete
    }
	
	public String userID;
	public String tid;
	public UserDeletedEvent(String userID, String tid) {
		this.userID = userID;
		this.tid = tid;
	}
}
