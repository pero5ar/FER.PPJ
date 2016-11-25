package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.*;

public class NKA extends Automaton {
    public static final String EPSILON = "$";

    public NKA(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, Set<StateSet>> prijelazi){
        super(pocetnoStanje, skupStanja, prijelazi);
    }


    public static NKA fromEpsilonNKA(EpsilonNKA enka){
        Set<StateSet> skupStanja = enka.getSkupStanja();
        DoubleMap<StateSet, String, Set<StateSet>> nkaPrijelazi = new DoubleMap<>();

        for(StateSet stanje : skupStanja){
            Map<String, Set<StateSet>> prijelazi = enka.getPrijelazi().get(stanje);
            for(Map.Entry<String, Set<StateSet>> entry : prijelazi.entrySet()) {
                if (entry.getKey().equals(EPSILON)) {
                    continue;
                }

                Set<StateSet> nkaStates = entry.getValue();
                Set<StateSet> nkaStates3 = new HashSet<>();
                for (StateSet nkaStates2 : nkaStates) {
                    StateSet nkaStates_copy = new StateSet(nkaStates2);
                    nkaStates_copy.forEach(s ->
                    {
                        StateSet tempSet = new StateSet();
                        tempSet.add(s);
                        nkaStates2.addAll(enka.getEpsilonOkruzenje(tempSet));
                    });
                    nkaStates3.add(nkaStates2);


                }
                nkaPrijelazi.put(stanje, entry.getKey(), nkaStates3);
            }
        }

        return new NKA(enka.pocetnoStanje, skupStanja, nkaPrijelazi);
    }

}
