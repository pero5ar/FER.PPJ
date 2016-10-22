package lab1.input;

import lab1.regex.RegexStorage;

import java.io.*;

/**
 * Created by pero5ar on 22.10.2016..
 */
public class LangRuleReader {

    public LangRuleReader(RegexStorage regexStorage)  {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String stateLine = readRegexRules(reader, regexStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readRegexRules(BufferedReader reader, RegexStorage regexStorage) throws IOException {
        String line;
        while (!(line = reader.readLine()).contains("%X")) {
            String name = line.substring(1,line.indexOf('}'));
            String definition = line.substring(line.indexOf('}')+1).trim();
            regexStorage.addDefinition(name, definition);
        }
        return line;
    }

    private void readStates(String stateLine) {
        String states;
    }
}
