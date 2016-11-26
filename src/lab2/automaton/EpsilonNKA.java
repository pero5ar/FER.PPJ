package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.HashSet;
import java.util.Set;

public class EpsilonNKA extends Automaton {
    public EpsilonNKA(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, Set<StateSet>> prijelazi) {
        super(pocetnoStanje, skupStanja, prijelazi);
    }

    public void addPrijelaz(StateSet source, String symbol, Set<StateSet> destination){
        prijelazi.put(source, symbol, destination);
    }


    public Set<StateSet> getEpsilonOkruzenje(Set<StateSet> states) {
        Set<StateSet> epsilonOkruzenje = new HashSet<>();
        Set<StateSet> tempSet = new HashSet<>();

        epsilonOkruzenje.addAll(states);
        boolean t= true;
        while(t) {
            t=false;
            tempSet.clear();
            for (StateSet set : epsilonOkruzenje) {
                if(getPrijelazi().get(set, NKA.EPSILON)== null){
                    continue;
                }
                for (StateSet epsPrijelazi : getPrijelazi().get(set, NKA.EPSILON)) {
                    //StateSet epsPrijelazi = getPrijelazi().get(set, NKA.EPSILON);
                    if(!epsilonOkruzenje.contains(epsPrijelazi)&&!tempSet.contains((epsPrijelazi))){
                        tempSet.add(epsPrijelazi);
                        t=true;
                    }
                }
            }
            epsilonOkruzenje.addAll(tempSet);
        }
        return epsilonOkruzenje;
    }

}
