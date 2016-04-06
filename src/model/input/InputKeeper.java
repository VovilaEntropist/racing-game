package model.input;

import java.util.HashSet;
import java.util.Set;

public class InputKeeper {
	private Set<KeyType> pressedKeys;
	
	public InputKeeper() {
		pressedKeys = new HashSet<KeyType>();
	}

	public void pressKey(KeyType keyCode) {
		pressedKeys.add(keyCode);
	}

	public void releaseKey(KeyType keyCode) {
		pressedKeys.remove(keyCode);
	}

	public boolean hasPressedKey(KeyType keyCode) {
		return pressedKeys.contains(keyCode);
	}

	@Override
	public String toString() {
		return pressedKeys.toString();
	}
}
