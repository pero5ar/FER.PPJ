package lab1.regex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RegexStorage  {
	private HashMap<String, Set<String>> storage;

	public RegexStorage(){
		this.storage = new HashMap<>();
	}

	public void addDefinition(String name, String definition){
		storage.put(name, parseDefinition(definition));
	}

	private Set<String> parseDefinition(String definition){
		String[] parts = definition.split("\\|");

		Set<String> set = new HashSet<>();
		for(String elem : parts){
			set.add(elem);
		}

		return set;
	}

	public static void main(String[] args){
		String test = "{oktalnaZnamenka}|8|9";

		RegexStorage rs = new RegexStorage();
		rs.addDefinition("oktalnaZnamenka", test);
	}
}
