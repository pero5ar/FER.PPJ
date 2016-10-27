package lab1.storage;

import lab1.models.Action;
import lab1.transform.Automat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RulesStorage implements Serializable {

	/**
	 * Maps state to map (regex -> list of actions)
	 * Order is important! (says Nicky)
	 */
	private LinkedHashMap<String, LinkedHashMap<Automat, List<Action>>> storage;

	public RulesStorage() {
		this.storage = new LinkedHashMap<>();
	}

	public void readRules(BufferedReader reader) throws IOException {
		//line by line reader
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				break;
			}
			/*
			 * prva linija - definicija: <imeStanja>regularniIzraz
			 * druga linija: {
			 * ...
			 * zadnja linija: }
			 */
			String stateName;
			Automat automat;
			List<Action> actions = new ArrayList<>();

			//ajmo parsirati liniju
			//sigh
			//ne da mi se ovo
			//a ovo je tek parsiranje :(
			int iOfGthan = line.indexOf('>');
			stateName = line.substring(1, iOfGthan);

			automat = new Automat(line.substring(iOfGthan + 1));

			reader.readLine();
			while (!(line = reader.readLine()).equals("}")) {
				Action action = Action.forLine(line);
				actions.add(action);
			}
			//System.out.println(automat.mapaPrijelaza.values());
			put(stateName, automat, actions);
		}
	}

	private void put(String stateName, Automat automat, List<Action> arguments) {
		LinkedHashMap<Automat, List<Action>> map = storage.get(stateName);
		if (map == null) {
			map = new LinkedHashMap<>();
			storage.put(stateName, map);
		}
		map.put(automat, arguments);
	}

	public LinkedHashMap<Automat, List<Action>> getRulesForState(String state) {
		return storage.get(state);
	}
}
