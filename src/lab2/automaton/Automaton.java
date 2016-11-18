package lab2.automaton;

import lab2.models.DoubleMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Automaton {
	protected Set<State> pocetnoStanje;
	protected Set<State> skupStanja;
	protected Set<String> skupSimbola;
	protected DoubleMap<Set<State>, String, Set<State>> prijelazi;

	public Automaton(Set<State> pocetnoStanje, Set<State> skupStanja, DoubleMap<Set<State>, String, Set<State>> prijelazi) {
		this.pocetnoStanje = pocetnoStanje;
		this.skupStanja = skupStanja;
		this.prijelazi = prijelazi;
	}

	public Set<State> getPocetnoStanje() {
		return pocetnoStanje;
	}

	public Set<State> getSkupStanja() {
		return skupStanja;
	}

	public Set<String> getSkupSimbola() {
		if (skupSimbola == null){
			skupSimbola = prijelazi.getKey2Set();
		}

		return skupSimbola;
	}

	public DoubleMap<Set<State>, String, Set<State>> getPrijelazi() {
		return prijelazi;
	}
}
