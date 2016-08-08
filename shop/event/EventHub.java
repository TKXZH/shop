package shop.event;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class EventHub {
	/*事件列表，负责存储所有注册事件*/
	private HashMap<EventType, ArrayList<Listener>> eventTable;
	public static EventHub eventHub;
	public static EventHub getInstence() {
		if(eventHub == null) {
			eventHub = new EventHub();
			return eventHub;
		}
		return eventHub;
	}
	private EventHub() {
		this.eventTable = new HashMap<EventType, ArrayList<Listener>>();
	}
	/**
	 * 
	 * @param eventType 待注册监听器的类别
	 * @param listener  监听器
	 */
	public void registListener(EventType eventType, Listener listener) {
		ArrayList<Listener> listeners = this.eventTable.get(eventType);
		/*将监听器分类加入监听器表*/
		if(listeners == null) {
			listeners = new ArrayList<Listener>();
			this.eventTable.put(eventType, listeners);
			listeners.add(listener);
		}
	}
	/**
	 * 广播此次事件
	 * @param eventType
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void dispatchEvent(EventType eventType, Event event) throws ClassNotFoundException, SQLException {
		ArrayList<Listener> listeners = this.eventTable.get(eventType);
		if(listeners == null) {
			return;
		}
		for(Listener listener : listeners) {
			listener.onEvent(eventType, event);
		}
	}
}
