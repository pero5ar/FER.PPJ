package lab1.storage;

import java.util.HashMap;

public class RegexStorage  {
	private HashMap<String, String> storage;

	public RegexStorage(){
		this.storage = new HashMap<>();
	}

	public void addDefinition(String name, String definition){
		storage.put(name, replaceByRef(definition));
	}

	private String replaceByRef(String definition){
		char[] chars = definition.toCharArray();

		String finalDef = "";
		boolean escaping = false;
		int byRef = -1;

		for(int i = 0; i < chars.length; i++){
			char c = chars[i];

			//escape
			if (c == '\\'){
				escaping = true;
				finalDef += c;
				continue;
			}

			//close byRef sequence
			if (byRef > -1 && c == '}'){
				String target = definition.substring(byRef+1, i);
				String replacement = storage.get(target);

				finalDef += '(' + replacement + ')';

				byRef = -1;
				continue;
			}

			//during byRef; don't save character
			if (byRef > -1){
				continue;
			}

			//open byRef
			if (!escaping && c == '{'){
				byRef = i;
				continue;
			}

			//reset escape character
			escaping = false;

			finalDef += c;
		}

		System.out.println(finalDef);
		return finalDef;
	}
}
