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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Automaton automaton = (Automaton) o;

		if (pocetnoStanje != null ? !pocetnoStanje.equals(automaton.pocetnoStanje) : automaton.pocetnoStanje != null)
			return false;
		return prijelazi != null ? prijelazi.equals(automaton.prijelazi) : automaton.prijelazi == null;

	}

	@Override
	public int hashCode() {
		int result = pocetnoStanje != null ? pocetnoStanje.hashCode() : 0;
		result = 31 * result + (prijelazi != null ? prijelazi.hashCode() : 0);
		return result;
	}
}
