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

				skupSimbola.forEach(s -> {
					if (prijelazi.get(novoStanje, s) != null){
						return;
					}
					prijelazi.put(novoStanje, s, statesTargets(prijelazi, novoStanje, s));
				});
			}
		}

		return new DKA(nka.pocetnoStanje, nka.skupStanja, prijelazi);
	}

	private static Set<State> statesTargets(DoubleMap<Set<State>, String, Set<State>> prijelazi, Set<State> states, String symbol){
		Set<State> stateTargets = new HashSet<>();
		states.forEach(s -> stateTargets.addAll(prijelazi.get(s.getSet(), symbol)));

		return stateTargets;
	}

	public static DKA fromEpsilonNKA(EpsilonNKA enka){
		NKA nka = NKA.fromEpsilonNKA(enka);
		return fromNKA(nka);
	}

}
