package lab1.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pero5ar on 22.10.2016..
 */
public abstract class InlineStorage implements Serializable {
	private List<String> storage;

	public InlineStorage() {
		storage = new ArrayList<String>();
	}

	public void add(String element) {
		storage.add(element);
	}

	public void add(Collection<String> newElements) {
		storage.addAll(newElements);
	}

	public List<String> getStorage() {
		return storage;
	}
}
