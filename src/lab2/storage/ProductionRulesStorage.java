package lab2.storage;

import lab2.models.Production;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class ProductionRulesStorage implements Serializable {

    private static final long serialVersionUID = 7748193148358682584L;

    public static final String EPSILON = "$";

    private LinkedHashMap<String, List<String[]>> storage;
    private List<Production> modeledStorage;

    public ProductionRulesStorage() {
        storage = new LinkedHashMap<>();
    }

    public void readToStorage(BufferedReader reader) throws IOException {
        boolean start = true;
        String currentKey = "";
        List<String[]> currentList = new ArrayList<>();
        String line;
        while (!("".equals(line = reader.readLine())) && line != null) {
            if (start) {
                currentKey = line.trim();
                start = false;
                continue;
            }
            if (!line.startsWith(" ")) {
                addToStorage(currentKey, currentList);
                currentKey = line.trim();
                currentList = new ArrayList<>();
            } else {
                currentList.add(line.trim().split(" "));
            }
        }
        addToStorage(currentKey, currentList);

        modeledStorage = createModeledStorage(storage);
    }

    private void addToStorage(String key, List<String[]> value) {
        if (storage.containsKey(key)) {
            List<String[]> oldValue = storage.get(key);
            value.addAll(oldValue);
        }
        storage.put(key, value);
    }

    public LinkedHashMap<String, List<String[]>> getStorage() {
        return storage;
    }

    public List<Production> getModeledStorage() {
        return modeledStorage;
    }

    public static List<Production> createModeledStorage(Map<String, List<String[]>> storage) {
        List<Production> modeledStorage = new LinkedList<>();
        storage.forEach((key, list) -> list.forEach(production -> modeledStorage.add(new Production(key, production))));
        return modeledStorage;
    }
}
