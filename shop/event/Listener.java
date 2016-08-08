package shop.event;

import java.sql.SQLException;

/*监听器接口*/
public interface Listener {
	public void onEvent(EventType type, Event e) throws ClassNotFoundException, SQLException;
}
