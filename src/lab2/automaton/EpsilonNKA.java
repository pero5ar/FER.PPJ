package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.HashSet;
import java.util.Set;

public class EpsilonNKA extends Automaton {
    public EpsilonNKA(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, StateSet> prijelazi) {
        super(pocetnoStanje, skupStanja, prijelazi);
    }

    public void addPrijelaz(StateSet source, String symbol, StateSet destination){
        prijelazi.put(source, symbol, destination);
    }


    public StateSet getEpsilonOkruzenje(StateSet state) {
        StateSet epsilonOkruzenje = new StateSet();

        for (StateSet set : skupStanja) {
            StateSet epsPrijelazi = getPrijelazi().get(set, NKA.EPSILON);
            epsilonOkruzenje.addAll(epsPrijelazi);

            epsPrijelazi.forEach(s ->
            {
                StateSet tempSet = new StateSet();
                tempSet.add(s);
                epsilonOkruzenje.addAll(getEpsilonOkruzenje(tempSet));
            });
        }

        return epsilonOkruzenje;
    }

}
