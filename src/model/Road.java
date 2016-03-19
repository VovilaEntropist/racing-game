package model;

import model.listener.EventData;
import model.listener.EventType;
import model.listener.ListenersList;
import model.listener.SenderType;

public class Road {
	private ListenersList listeners;

	private PhysicalBody body;
	private int laneCount;

	public Road(PhysicalBody body, int laneCount, ListenersList listeners) {
		this.listeners = listeners;
		this.laneCount = laneCount;

		setBody(body);
	}

	public PhysicalBody getBody() {
		return body;
	}

	public void setBody(PhysicalBody body) {
		this.body = body;

		listeners.notify(new EventData(SenderType.ROAD, EventType.INITIALIZE, this));
	}

	public int getLaneCount() {
		return laneCount;
	}

	public void setLaneCount(int laneCount) {
		this.laneCount = laneCount;
	}

}
