package lab1.input;

import lab1.regex.RegexStorage;
import lab1.storage.InlineStorage;
import lab1.storage.StateStorage;
import lab1.storage.TokenStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by pero5ar on 22.10.2016..
 */
public class LangRuleReader {

    private final String STATE_LINE_START = "%X";
    private final String TOKEN_LINE_START = "%L";

    /**
     *
     *
     * @param regexStorage
     * @param stateStorage
     * @param tokenStorage
     */
    public LangRuleReader(RegexStorage regexStorage, StateStorage stateStorage, TokenStorage tokenStorage)  {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String stateLine = readRegexRules(reader, regexStorage);
            readLineElements(stateLine, stateStorage, STATE_LINE_START);
            readLineElements(reader.readLine(), tokenStorage, TOKEN_LINE_START);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills RegexStorage
     *
     * @param reader
     * @param regexStorage
     * @return              last line read, contains states
     * @throws IOException  if reader breaks
     */
    private String readRegexRules(BufferedReader reader, RegexStorage regexStorage) throws IOException {
        String line;
        while (!(line = reader.readLine()).startsWith(STATE_LINE_START)) {
            String name = line.substring(1,line.indexOf('}'));
            String definition = line.substring(line.indexOf('}')+1).trim();
            regexStorage.addDefinition(name, definition);
        }
        return line;
    }

    /**
     * Fills StateStorage or TokenStorage
     *
     * @param line
     * @param storage
     * @param toRemove  element to remove from line
     */
    private void readLineElements(String line, InlineStorage storage, String toRemove) {
        String[] elementsArray = line.split(" ");
        Collection<String> elements = new ArrayList<>(Arrays.asList(elementsArray));
        elements.remove(toRemove);
        storage.add(elements);
    }
}
