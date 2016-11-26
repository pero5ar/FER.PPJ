package lab2.automaton;

import lab1.storage.StateStorage;
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

	public static final String EPSILON = "$";


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

		DoubleMap<StateSet, String, Set<StateSet>> dkaPrijelazi = new DoubleMap<>();
		Set<StateSet> dkaSkupStanja = new HashSet<>();

		StateSet pocetnoStanje = enka.getPocetnoStanje();
		Set<StateSet> pocetnoStanjeSet = new HashSet<>();
		pocetnoStanjeSet.add(pocetnoStanje);
		pocetnoStanjeSet.addAll(enka.getEpsilonOkruzenje(pocetnoStanjeSet));
		for(StateSet set : pocetnoStanjeSet){
			for(String s : set){
				pocetnoStanje.add(s);
			}
		}
		boolean t = true;

		Set<StateSet> oldSet = new HashSet<>();
		Set<StateSet> newSet = new HashSet<>();

		oldSet.add(pocetnoStanje);
		dkaSkupStanja.add(pocetnoStanje);

		while(t){
			t=false;
			for(StateSet set : oldSet){

				for(String prijelazniZnak : enka.getPrijelazi().getKey2Set()) {
					Set<StateSet> tempSet = new HashSet<>();
					if (prijelazniZnak.equals(EPSILON)) {
						continue;
					}

					for (String s : set) {
						StateSet tempStateSet = new StateSet();
						tempStateSet.add(s);
						if(enka.getPrijelazi().get(tempStateSet, prijelazniZnak)==null){
							continue;
						}
						for(StateSet stateSet : enka.getPrijelazi().get(tempStateSet, prijelazniZnak)){
							tempSet.add(stateSet);
						}
					}
					tempSet=enka.getEpsilonOkruzenje(tempSet);
					StateSet tempStateSet = new StateSet();
					for(StateSet stateSet : tempSet){

						tempStateSet.addAll(stateSet);
					}
					tempSet = new HashSet<>();
					tempSet.add(tempStateSet);

					if(tempStateSet.size()==0){
						continue;
					}

					dkaPrijelazi.put(set, prijelazniZnak,tempSet );
					if(!dkaSkupStanja.contains(tempStateSet)){
						newSet.add(tempStateSet);
						dkaSkupStanja.add(tempStateSet);
						t=true;
					}

				}
			}

			oldSet = new HashSet<>();
			oldSet.addAll(newSet);
			newSet = new HashSet<>();
		}

		return new DKA(enka.pocetnoStanje, dkaSkupStanja, dkaPrijelazi);
	}


	public boolean equals(Object other){
		return super.equals(other);
	}

	public Set<StateSet> getSetStateSet(){
		return prijelazi.getMap().keySet();
	}

}
