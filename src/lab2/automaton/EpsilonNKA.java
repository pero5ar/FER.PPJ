package lab2.automaton;

import lab2.models.DoubleMap;

import java.util.HashSet;
import java.util.Set;

public class EpsilonNKA extends Automaton {
    public EpsilonNKA(Set<State> pocetnoStanje, Set<State> skupStanja, DoubleMap<Set<State>, String, Set<State>> prijelazi) {
        super(pocetnoStanje, skupStanja, prijelazi);
    }

    public void addPrijelaz(Set<State> source, String symbol, Set<State> destination){
        prijelazi.put(source, symbol, destination);
    }

    public Set<State> getEpsilonOkruzenje(State state) {
        Set<State> epsilonOkruzenje = new HashSet<>();

        for (State stanje : skupStanja) {
            Set<State> epsPrijelazi = getPrijelazi().get(stanje.getSet(), NKA.EPSILON);
            epsilonOkruzenje.addAll(epsPrijelazi);

            epsPrijelazi.forEach(s -> epsilonOkruzenje.addAll(getEpsilonOkruzenje(s)));
        }

        return epsilonOkruzenje;
    }
}
