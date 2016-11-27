package common;

import java.util.ArrayList;

/**
 * {@link InlineDataStorage} that uses {@link ArrayList} as internal storage.
 * Slower than {@link InlineDataStorageSet}, but allows multiple instances of object to be stored.
 */
public abstract class InlineDataStorageList extends InlineDataStorage {
	private static final long serialVersionUID = 121459582349935953L;

	public InlineDataStorageList(){
		storage = new ArrayList<>();
}

	public ArrayList<String> getTypedStorage(){
		return (ArrayList<String>) storage;
	}
}
