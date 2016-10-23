package lab1.storage;

import lab1.models.Action;
import lab1.models.RegEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RulesStorage {

	/**
	 * Maps state to map (regex -> list of actions)
	 * Order is important! (says Nicky)
	 */
	private LinkedHashMap<String, LinkedHashMap<RegEx, List<Action>>> storage;

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
			String stateName;
			RegEx regex;
			List<Action> actions = new ArrayList<>();

			//ajmo parsirati liniju
			//sigh
			//ne da mi se ovo
			//a ovo je tek parsiranje :(
			int iOfGthan = line.indexOf('>');
			stateName = line.substring(1, iOfGthan);
			regex = new RegEx(line.substring(iOfGthan+1));

			reader.readLine();
			while(!(line = reader.readLine()).equals("}")){
				Action action = new Action(line);
				actions.add(action);
			}
			put(stateName, regex, actions);
		}
	}

	private void put(String stateName, RegEx regex, List<Action> arguments){
		LinkedHashMap<RegEx, List<Action>> map = storage.get(stateName);
		if (map == null){
			map = new LinkedHashMap<>();
			storage.put(stateName, map);
		}
		map.put(regex, arguments);
	}

	public LinkedHashMap<RegEx, List<Action>> getRulesForState(String state){
		return storage.get(state);
	}
}
