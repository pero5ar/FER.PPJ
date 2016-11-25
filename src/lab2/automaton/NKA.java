package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.*;

public class NKA extends Automaton {
    public static final String EPSILON = "$";

    public NKA(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, StateSet> prijelazi){
        super(pocetnoStanje, skupStanja, prijelazi);
    }


    public static NKA fromEpsilonNKA(EpsilonNKA enka){
        Set<StateSet> skupStanja = enka.getSkupStanja();
        DoubleMap<StateSet, String, StateSet> nkaPrijelazi = new DoubleMap<>();

        for(StateSet stanje : skupStanja){
            Map<String, StateSet> prijelazi = enka.getPrijelazi().get(stanje);
            for(Map.Entry<String, StateSet> entry : prijelazi.entrySet()){
                if (entry.getKey().equals(EPSILON)){
                    continue;
                }

                StateSet nkaStates = entry.getValue();
                StateSet nkaStates_copy = new StateSet(nkaStates);
                nkaStates_copy.forEach(s ->
                {
                    StateSet tempSet = new StateSet();
                    tempSet.add(s);
                    nkaStates.addAll(enka.getEpsilonOkruzenje(tempSet));
                });

                nkaPrijelazi.put(stanje, entry.getKey(), nkaStates);
            }
        }

        return new NKA(enka.pocetnoStanje, skupStanja, nkaPrijelazi);
    }

}
