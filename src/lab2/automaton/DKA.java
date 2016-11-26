package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class  DKA extends Automaton {

	/**
	 * From ε-NKA arguments
	 * @param pocetnoStanje
	 * @param skupStanja
	 * @param prijelazi State sets must have only one element!
	 */
	public DKA(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, Set<StateSet>> prijelazi) {
		super(pocetnoStanje, skupStanja, prijelazi);
	}


	public static DKA fromNKA(NKA nka){
		DoubleMap<StateSet, String, Set<StateSet>> prijelazi = new DoubleMap<>(nka.prijelazi);

		DoubleMap<StateSet, String, Set<StateSet>> prijelazi_copy = new DoubleMap<>(prijelazi);


		Set<String> skupSimbola = nka.getSkupSimbola();
		for (Map.Entry<StateSet, Map<String,Set<StateSet>>> entry : prijelazi_copy.getMap().entrySet()) {
			StateSet stanje = entry.getKey();
			for (Map.Entry<String, Set<StateSet>> statePrijelaz : entry.getValue().entrySet()) {
				String simbol = statePrijelaz.getKey();
                Set<StateSet> novaStanja = statePrijelaz.getValue();


                    putStatesTargets(prijelazi, novaStanja, skupSimbola);

			}
		}

		return new DKA(nka.pocetnoStanje, nka.skupStanja, prijelazi);
	}



	private static void putStatesTargets(DoubleMap<StateSet, String,Set<StateSet>> prijelazi, Set<StateSet> novaStanja, Set<String> skupSimbola){
		if (novaStanja.isEmpty()){
			return;
		}
        for(StateSet novoStanje: novaStanja) {
            skupSimbola.forEach(simbol -> {
                if (prijelazi.get(novoStanje, simbol) != null) {
                    return;
                }
                Set<StateSet> targets = new HashSet<StateSet>();
                novoStanje.forEach(state -> {
                    StateSet tempSet = new StateSet();
                    tempSet.add(state);
                    targets.addAll(prijelazi.get(tempSet, simbol));
                });

                prijelazi.put(novoStanje, simbol, targets);

                putStatesTargets(prijelazi, targets, skupSimbola);
            });
        }
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
