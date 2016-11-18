package common;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.HashSet;

/**
 * {@link InlineDataStorage} that uses {@link HashSet} as internal storage.
 * Much faster than {@link InlineDataStorageList}, but allows only one instance of object to be stored.
 */
public abstract class InlineDataStorageSet extends InlineDataStorage {
	public InlineDataStorageSet(){
		storage = new HashSet<>();
	}

	public HashSet<String> getTypedStorage(){
		return (HashSet<String>) storage;
	}
}
