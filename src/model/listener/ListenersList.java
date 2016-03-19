package model.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListenersList {
	public List<Listener> listeners = new ArrayList<Listener>();

	public boolean add(Listener listener) {
		return listeners.add(listener);
	}

	public boolean remove(Listener listener) {
		return listeners.remove(listener);
	}

	public void notify(EventData eventData) {
		ListIterator<Listener> iterator = listeners.listIterator();
		while (iterator.hasNext()) {
			iterator.next().handleEvent(eventData);
		}
	}
}
