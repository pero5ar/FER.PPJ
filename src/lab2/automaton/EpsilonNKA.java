package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.HashSet;
import java.util.Set;

public class EpsilonNKA extends Automaton {
    public EpsilonNKA(StateSet pocetnoStanje, StateSet skupStanja, DoubleMap<StateSet, String, StateSet> prijelazi) {
        super(pocetnoStanje, skupStanja, prijelazi);
    }

    public void addPrijelaz(StateSet source, String symbol, StateSet destination){
        prijelazi.put(source, symbol, destination);
    }

    /*
    public StateSet getEpsilonOkruzenje(State state) {
        StateSet epsilonOkruzenje = new StateSet();

        for (State stanje : skupStanja) {
            StateSet epsPrijelazi = getPrijelazi().get(stanje.getSet(), NKA.EPSILON);
            epsilonOkruzenje.addAll(epsPrijelazi);

            epsPrijelazi.forEach(s -> epsilonOkruzenje.addAll(getEpsilonOkruzenje(s)));
        }

        return epsilonOkruzenje;
    }
     */
}
