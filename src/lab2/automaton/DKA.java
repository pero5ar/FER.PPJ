package lab2.automaton;

import lab2.models.DoubleMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DKA extends Automaton {

	/**
	 * From ε-NKA arguments
	 * @param pocetnoStanje
	 * @param skupStanja
	 * @param prijelazi State sets must have only one element!
	 */
	public DKA(Set<State> pocetnoStanje, Set<State> skupStanja, DoubleMap<Set<State>, String, Set<State>> prijelazi) {
		super(pocetnoStanje, skupStanja, prijelazi);
	}

	public static DKA fromNKA(NKA nka){
		DoubleMap<Set<State>, String, Set<State>> prijelazi = new DoubleMap<>(nka.prijelazi);

		DoubleMap<Set<State>, String, Set<State>> prijelazi_copy = new DoubleMap<>(prijelazi);


		Set<String> skupSimbola = nka.getSkupSimbola();
		for (Map.Entry<Set<State>, Map<String, Set<State>>> entry : prijelazi_copy.getMap().entrySet()) {
			Set<State> stanje = entry.getKey();
			for (Map.Entry<String, Set<State>> statePrijelaz : entry.getValue().entrySet()) {
				String simbol = statePrijelaz.getKey();
				Set<State> novoStanje = statePrijelaz.getValue();

				putStatesTargets(prijelazi, novoStanje, skupSimbola);
			}
		}

		return new DKA(nka.pocetnoStanje, nka.skupStanja, prijelazi);
	}

	private static void putStatesTargets(DoubleMap<Set<State>, String, Set<State>> prijelazi, Set<State> novoStanje, Set<String> skupSimbola){
		if (novoStanje.isEmpty()){
			return;
		}

		skupSimbola.forEach(simbol -> {
			if (prijelazi.get(novoStanje, simbol) != null){
				return;
			}
			Set<State> targets = new HashSet<>();
			novoStanje.forEach(state -> {
				targets.addAll(prijelazi.get(state.getSet(), simbol));
			});

			prijelazi.put(novoStanje, simbol, targets);

			putStatesTargets(prijelazi, targets, skupSimbola);
		});
	}

	public static DKA fromEpsilonNKA(EpsilonNKA enka){
		NKA nka = NKA.fromEpsilonNKA(enka);
		return fromNKA(nka);
	}

	public boolean equals(Object other){
		return super.equals(other);
	}

}
