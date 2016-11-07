package lab1.input;

import common.InlineDataStorage;
import lab1.storage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by pero5ar on 22.10.2016..
 */
public class LangRuleReader {

	private static final String STATE_LINE_START = "%X";
	private static final String TOKEN_LINE_START = "%L";

	/**
	 * @param regexStorage
	 * @param stateStorage
	 * @param tokenStorage
	 */
	public static void read(RegexStorage regexStorage, StateStorage stateStorage, TokenStorage tokenStorage, RulesStorage rulesStorage) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
		//try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\CHOPPER\\Desktop\\PPJ\\integration\\simplePpjLang.lan"))) {
			String stateLine = readRegexRules(reader, regexStorage);
			InlineDataStorage.readLineToStorage(stateLine, stateStorage, STATE_LINE_START);
			InlineDataStorage.readLineToStorage(reader.readLine(), tokenStorage, TOKEN_LINE_START);
			rulesStorage.readRules(reader, regexStorage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills RegexStorage
	 *
	 * @param reader
	 * @param regexStorage
	 * @return last line read, contains states
	 * @throws IOException if reader breaks
	 */
	private static String readRegexRules(BufferedReader reader, RegexStorage regexStorage) throws IOException {
		String line;
		while (!(line = reader.readLine()).startsWith(STATE_LINE_START)) {
			String name = line.substring(1, line.indexOf('}'));
			String definition = line.substring(line.indexOf('}') + 1).trim();
			regexStorage.addDefinition(name, definition);
		}

		return line;
	}

}
