package shop.eventImp;

import shop.event.Event;
import shop.event.EventType;
import shop.event.Listener;
import shop.filter.DeletedRules;

public class UserDeletedListener implements Listener {
	@Override
	public void onEvent(EventType type, Event e) {
		UserDeletedEvent event = (UserDeletedEvent)e;
		DeletedRules.getInstence().addRules(event.userID,event.tid);
	}
}
