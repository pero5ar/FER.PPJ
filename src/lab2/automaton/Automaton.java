package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.Set;

public abstract class Automaton {
	protected StateSet pocetnoStanje;
	protected Set<StateSet> skupStanja;
	protected Set<String> skupSimbola;
	protected DoubleMap<StateSet, String, StateSet> prijelazi;

	public Automaton(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, StateSet> prijelazi) {
		this.pocetnoStanje = pocetnoStanje;
		this.skupStanja = skupStanja;
		this.prijelazi = prijelazi;
	}

	public StateSet getPocetnoStanje() {
		return pocetnoStanje;
	}

	public Set<StateSet> getSkupStanja() {
		return skupStanja;
	}

	public Set<String> getSkupSimbola() {
		if (skupSimbola == null){
			skupSimbola = prijelazi.getKey2Set();
		}

		return skupSimbola;
	}

	public DoubleMap<StateSet, String, StateSet> getPrijelazi() {
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
