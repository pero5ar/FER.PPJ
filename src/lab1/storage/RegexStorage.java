package lab1.storage;

import java.io.Serializable;
import java.util.HashMap;

public class RegexStorage implements Serializable {
	private HashMap<String, String> storage;

	public RegexStorage() {
		this.storage = new HashMap<>();
	}

	public void addDefinition(String name, String definition) {
		storage.put(name, replaceByRef(definition));
	}

	/**
	 * Obavlja zamjene referenci na regularne izraze. Npr. mijenja {znamenka} s (0|1|2|3|4|5|6|7|8|9).
	 *
	 * @param definition String
	 * @return String
	 */
	public String replaceByRef(String definition) {
		char[] chars = definition.toCharArray();

		String finalDef = "";
		boolean escaping = false;
		int byRef = -1;

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			//escape
			if (!escaping && c == '\\') {
				escaping = true;
				finalDef += c;
				continue;
			}

			//close byRef sequence
			if (byRef > -1 && c == '}') {
				String target = definition.substring(byRef + 1, i);
				String replacement = storage.get(target);

				finalDef += '(' + replacement + ')';

				byRef = -1;
				continue;
			}

			//during byRef; don't save character
			if (byRef > -1) {
				continue;
			}

			//open byRef
			if (!escaping && c == '{') {
				byRef = i;
				continue;
			}

			//reset escape character
			escaping = false;

			finalDef += c;
		}

		return finalDef;
	}
}
