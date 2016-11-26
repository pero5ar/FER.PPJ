package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.Set;

public abstract class Automaton {
	protected StateSet pocetnoStanje;
	protected Set<StateSet> skupStanja;
	protected Set<String> skupSimbola;
	protected DoubleMap<StateSet, String, Set<StateSet>> prijelazi;

	public Automaton(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, Set<StateSet>> prijelazi) {
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

	public DoubleMap<StateSet, String, Set<StateSet>> getPrijelazi() {
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

	public void ispisPrijelaza(){


		int i = 0;

		for(StateSet set : this.getPrijelazi().getMap().keySet()){
			//System.out.println(set.isEmpty() + Integer.toString(i++));

			for(String znak : this.getPrijelazi().get(set).keySet()) {
                for (StateSet set2 : this.getPrijelazi().get(set, znak)) {
                    for (String state : set) {
                        System.out.print(state + "|||");
                    }
                    System.out.print("----" + znak + "----");
				    for(String state2 : set2){
					    System.out.print(state2+",");
				    }
				    System.out.println();
                }
            }

		}

	}
}
