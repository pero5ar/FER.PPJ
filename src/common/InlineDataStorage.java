package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by pero5ar on 7.11.2016..
 */
public abstract class InlineDataStorage implements Serializable {
    private static final long serialVersionUID = 6354904397308091181L;

    private List<String> storage;

    public InlineDataStorage() {
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

    /**
     * Fills InlineDataStorage with data elements
     *
     * @param line      line read form input that contains the data elements
     * @param storage   storage to fill
     * @param toRemove  element to remove from line
     */
    public static void readLineToStorage(String line, InlineDataStorage storage, String toRemove) {
        String[] elementsArray = line.split(" ");
        Collection<String> elements = new ArrayList<>(Arrays.asList(elementsArray));
        elements.remove(toRemove);
        storage.add(elements);
    }
}
