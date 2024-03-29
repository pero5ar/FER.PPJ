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
    private static final long serialVersionUID = 9055222797327608438L;

    protected String first;

    protected Collection<String> storage;

    public void add(String element) {
        if (first == null){
            first = element;
        }
        storage.add(element);
    }

    private void addAll(Collection<String> newElements) {
        storage.addAll(newElements);
    }

    public Collection<String> getStorage() {
        return storage;
    }

    /**
     * Fills this InlineDataStorage with data elements
     *
     * @param line      line read from input that contains the data elements
     * @param toRemove  elements to remove from line
     */
    public void readLine(String line, String ... toRemove) {
        String[] elementsArray = line.split(" ");
        List<String> elements = new ArrayList<>(Arrays.asList(elementsArray));

        List<String> toRemoveList = new ArrayList<>(Arrays.asList(toRemove));
        elements.removeAll(toRemoveList);

        first = elements.get(0);
        addAll(elements);
    }

	/**
     * Fills InlineDataStorage with data elements
     *
     * @Deprecated Use non-static readLine instead.
     * @param line      line read from input that contains the data elements
     * @param storage   storage to fill
     * @param toRemove  element to remove from line
     */
    public static void readLineToStorage(String line, InlineDataStorage storage, String toRemove) {
        storage.readLine(line, toRemove);
    }

    public String getFirst(){
        if (first == null){
            throw new RuntimeException("No data loaded.");
        }

        return first;
    }
}
