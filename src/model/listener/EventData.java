package model.listener;

public class EventData {
	private SenderType senderType;
	private EventType eventType;
	private Object[] objects;

	public EventData(SenderType senderType, EventType eventType, Object... objects) {
		this.senderType = senderType;
		this.eventType = eventType;
		this.objects = objects;
	}

	public EventData(SenderType senderType, EventType eventType, Object object) {
		this(senderType, eventType, new Object[] {object});
	}

	public EventData(SenderType senderType, EventType eventType) {
		this(senderType, eventType, null);
	}

	public SenderType getSenderType() {
		return senderType;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Object getObject(int index) {
		try {
			return objects[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Object getObject() {
		return getObject(0);
	}

	public int getObjectCount() {
		return objects.length;
	}

}
