package lab1.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RegexStorage  {
	private HashMap<String, String> storage;

	public RegexStorage(){
		this.storage = new HashMap<>();
	}

	public void addDefinition(String name, String definition){
		storage.put(name, definition);
	}

	@Deprecated
	private Set<String> parseDefinition(String definition){
		String[] parts = definition.split("\\|");

		Set<String> set = new HashSet<>();
		for(String elem : parts){
			set.add(elem);
		}

		return set;
	}
}
