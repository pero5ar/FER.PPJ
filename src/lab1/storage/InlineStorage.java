package lab1.storage;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by pero5ar on 22.10.2016..
 */
public abstract class InlineStorage {
    private Collection<String> storage;

    public InlineStorage() {
        storage = new HashSet<String>();
    }

    public void add(String element) {
        storage.add(element);
    }

    public void add(Collection<String> newElements) {
        storage.addAll(newElements);
    }

    public Collection<String> getStorage() {
        return storage;
    }
}
