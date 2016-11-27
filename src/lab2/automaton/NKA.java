package lab2.automaton;

import lab2.models.DoubleMap;
import lab2.models.StateSet;

import java.util.Set;

public class NKA extends Automaton {


    public NKA(StateSet pocetnoStanje, Set<StateSet> skupStanja, DoubleMap<StateSet, String, Set<StateSet>> prijelazi){
        super(pocetnoStanje, skupStanja, prijelazi);
    }


    /*public static NKA fromEpsilonNKA(EpsilonNKA enka){
        Set<StateSet> skupStanja = enka.getSkupStanja();
        System.out.print(skupStanja.size());
        DoubleMap<StateSet, String, Set<StateSet>> nkaPrijelazi = new DoubleMap<>();

        for(StateSet stanje : skupStanja){
            if(enka.getPrijelazi().get(stanje)==null){
                continue;
            }

            Map<String, Set<StateSet>> prijelazi = enka.getPrijelazi().get(stanje);
            for(String prijelazniZnak: enka.getPrijelazi().getKey2Set()) {
                if (prijelazniZnak.equals(EPSILON)) {
                    continue;
                }
                Set<StateSet> set = new HashSet<>();
                Set<StateSet> newSet = new HashSet<>();
                Set<StateSet> newSetCopy = new HashSet<>();
                set.add(stanje);
                Set<StateSet> setCopy = new HashSet<>();
                setCopy.add(stanje);
                set=enka.getEpsilonOkruzenje(setCopy);

                for(StateSet ss : set){
                    if(enka.getPrijelazi().get(ss,prijelazniZnak)==null){
                        continue;
                    }
                    for(StateSet ss2 : enka.getPrijelazi().get(ss, prijelazniZnak)){
                        newSet.add(ss2);
                    }
                }
                newSetCopy.addAll(newSet);
                newSet=enka.getEpsilonOkruzenje(newSetCopy);

                nkaPrijelazi.put(stanje, prijelazniZnak, newSet);
            }
        }

        return new NKA(enka.pocetnoStanje, skupStanja, nkaPrijelazi);
    }*/

}
