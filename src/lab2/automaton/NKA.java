package lab2.automaton;

import lab2.models.DoubleMap;

import java.util.*;

public class NKA extends Automaton {
    public static final String EPSILON = "$";

    public NKA(Set<State> pocetnoStanje, Set<State> skupStanja, DoubleMap<Set<State>, String, Set<State>> prijelazi){
        super(pocetnoStanje, skupStanja, prijelazi);
    }

    public static NKA fromEpsilonNKA(EpsilonNKA enka){
        Set<State> skupStanja = enka.getSkupStanja();
        DoubleMap<Set<State>, String, Set<State>> nkaPrijelazi = new DoubleMap<>();

        for(State stanje : skupStanja){
            Map<String, Set<State>> prijelazi = enka.getPrijelazi().get(stanje.getSet());
            for(Map.Entry<String, Set<State>> entry : prijelazi.entrySet()){
                if (entry.getKey().equals(EPSILON)){
                    continue;
                }

                Set<State> nkaStates = entry.getValue();
                Set<State> nkaStates_copy = new HashSet<>(nkaStates);
                nkaStates_copy.forEach(s -> nkaStates.addAll(enka.getEpsilonOkruzenje(s)));

                nkaPrijelazi.put(stanje.getSet(), entry.getKey(), nkaStates);
            }
        }

        return new NKA(enka.pocetnoStanje, skupStanja, nkaPrijelazi);
    }
}
