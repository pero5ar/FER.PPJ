package lab1.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class RulesStorage {

	/**
	 * Order is important! (says Nicky)
	 */
	private LinkedHashMap<String, HashMap<String, List<String>>> storage;

	public RulesStorage(){
		this.storage = new LinkedHashMap<>();
	}

	public void readRules(BufferedReader reader) throws IOException {
		//line by line reader
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()){
				break;
			}
			/*
			 * prva linija - definicija: <imeStanja>regularniIzraz
			 * druga linija: {
			 * ...
			 * zadnja linija: }
			 */
			String imeStanja;
			String regularniIzraz;
			List<String> argumenti = new ArrayList<>();

			//ajmo parsirati liniju
			//sigh
			//ne da mi se ovo
			//a ovo je tek parsiranje :(
			int iOfGthan = line.indexOf('>');
			imeStanja = line.substring(1, iOfGthan);
			regularniIzraz = line.substring(iOfGthan+1);

			reader.readLine();
			while(!(line = reader.readLine()).equals("}")){
				argumenti.add(line);
			}
			put(imeStanja, regularniIzraz, argumenti);
		}
	}

	private void put(String imeStanja, String regularniIzraz, List<String> argumenti){
		HashMap<String, List<String>> map = storage.get(imeStanja);
		if (map == null){
			map = new HashMap<>();
			storage.put(imeStanja, map);
		}
		map.put(regularniIzraz, argumenti);
	}
}
