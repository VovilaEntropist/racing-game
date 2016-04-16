package model.action;

import java.util.HashSet;
import java.util.Set;

public class ActionKeeper {
	private Set<ActionType> actions;

	public ActionKeeper() {
		actions = new HashSet<ActionType>();
	}

	public void addAction(ActionType actionType) {
		actions.add(actionType);
	}

	public void removeAction(ActionType actionType) {
		actions.remove(actionType);
	}

	public boolean hasAction(ActionType actionType) {
		return actions.contains(actionType);
	}

	@Override
	public String toString() {
		return actions.toString();
	}
}
