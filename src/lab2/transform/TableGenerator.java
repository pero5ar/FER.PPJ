package lab2.transform;

import lab2.automaton.DKA;
import lab2.models.DoubleMap;
import lab2.models.Production;
import lab2.models.StateSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by CHOPPER on 11/26/2016.
 */
public class TableGenerator {


    public DoubleMap<String, String, String> generateActionTable(DKA dka) {
        DoubleMap<String, String, String> actionTable = new DoubleMap<>();
        for (StateSet state : dka.getPrijelazi().getMap().keySet()) {
            for (String prod : state) {

                String lijevaStranaProdukcije = prod.split("->")[0];
                String desnaStranaProdukcije = prod.split("->")[1];

                String prijeOznakeTocke = desnaStranaProdukcije.split("<OznakaTocke>")[0];
                String svePoslijeOznakeTocke = desnaStranaProdukcije.split("<OznakaTocke>")[1];

                String poslijeOznakeTocke = svePoslijeOznakeTocke.split("\\{")[0];
                String uglate = svePoslijeOznakeTocke.split("\\{")[1];
                uglate = uglate.split("\\}")[0];

                String[] znakoviUUglatima = uglate.split(",");

                if (poslijeOznakeTocke != null && !poslijeOznakeTocke.equals(" ") && !poslijeOznakeTocke.startsWith("<")) {
                    for (StateSet tempState : dka.getPrijelazi().get(state, poslijeOznakeTocke.substring(0, 1))) {
                        actionTable.put(state.getStateName(), poslijeOznakeTocke.substring(0, 1), "P(" + tempState.getStateName() + ")");
                    }
                } else if (!lijevaStranaProdukcije.equals("NovoPocetnoStanje") && prijeOznakeTocke != null && poslijeOznakeTocke == null) {
                    for (int i = 0; i < znakoviUUglatima.length; i++) {
                        actionTable.put(state.getStateName(), znakoviUUglatima[i], "R(" + lijevaStranaProdukcije + "->" + prijeOznakeTocke + ")");
                    }

                } else if (lijevaStranaProdukcije.equals("NovoPocetnoStanje") && poslijeOznakeTocke == null) {
                    actionTable.put(state.getStateName(), "<OznakaKrajaNiza>", "O()");
                }


            }
        }
        return actionTable;
    }

    public DoubleMap<String, String, String> generateNewStateTable(DKA dka) {
        DoubleMap<String, String, String> newStateTable = new DoubleMap<>();
        for (StateSet state : dka.getPrijelazi().getMap().keySet()) {
            Map<String, Set<StateSet>> tempMap = dka.getPrijelazi().get(state);
            for (String prijelazni : tempMap.keySet()) {
                if (prijelazni.startsWith("<")) {
                    for (StateSet tempStateSet : tempMap.get(prijelazni)) {
                        newStateTable.put(state.getStateName(), prijelazni, tempStateSet.getStateName());
                    }
                }
            }

        }
        return newStateTable;
    }

}
