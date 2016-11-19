package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class  DKA extends Automaton {


	private String broj;

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	/**
	 * From ε-NKA arguments
	 * @param pocetnoStanje
	 * @param skupStanja
	 * @param prijelazi State sets must have only one element!
	 */
	public DKA(StateSet pocetnoStanje, StateSet skupStanja, DoubleMap<StateSet, String, StateSet> prijelazi) {
		super(pocetnoStanje, skupStanja, prijelazi);
	}

	public static DKA fromNKA(NKA nka){
		DoubleMap<StateSet, String, StateSet> prijelazi = new DoubleMap<>(nka.prijelazi);

		DoubleMap<StateSet, String, StateSet> prijelazi_copy = new DoubleMap<>(prijelazi);


		Set<String> skupSimbola = nka.getSkupSimbola();
		for (Map.Entry<StateSet, Map<String, StateSet>> entry : prijelazi_copy.getMap().entrySet()) {
			StateSet stanje = entry.getKey();
			for (Map.Entry<String, StateSet> statePrijelaz : entry.getValue().entrySet()) {
				String simbol = statePrijelaz.getKey();
				StateSet novoStanje = statePrijelaz.getValue();

				putStatesTargets(prijelazi, novoStanje, skupSimbola);
			}
		}

		return new DKA(nka.pocetnoStanje, nka.skupStanja, prijelazi);
	}

	private static void putStatesTargets(DoubleMap<StateSet, String, StateSet> prijelazi, StateSet novoStanje, Set<String> skupSimbola){
		if (novoStanje.isEmpty()){
			return;
		}

		skupSimbola.forEach(simbol -> {
			if (prijelazi.get(novoStanje, simbol) != null){
				return;
			}
			StateSet targets = new StateSet();
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

	public Set<StateSet> getSetStateSet(){
		return prijelazi.getMap().keySet();
	}

}
