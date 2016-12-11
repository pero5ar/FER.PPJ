package lab2.transform;

import lab2.automaton.DKA;
import lab2.models.DoubleMap;
import lab2.models.Production;
import lab2.models.StateSet;
import lab2.storage.ProductionRulesStorage;

import java.util.Map;
import java.util.Set;

/**
 * Created by CHOPPER on 11/26/2016.
 */
public class TableGenerator {

    public static DoubleMap<String, String, String> generateActionTable(DKA dka, ProductionRulesStorage storage) {
        DoubleMap<String, String, String> actionTable = new DoubleMap<>();
        for (StateSet state : dka.getSkupStanja()) {
            for (String prod : state) {
                String lijevaStranaProdukcije = prod.split("->")[0];
                String desnaStranaProdukcije = prod.split("->")[1];

                String prijeOznakeTocke = desnaStranaProdukcije.split("<OznakaTocke>")[0];
                String svePoslijeOznakeTocke = desnaStranaProdukcije.split("<OznakaTocke>")[1];

                String poslijeOznakeTocke = svePoslijeOznakeTocke.split("\\{")[0].trim();
                String uglate = svePoslijeOznakeTocke.split("\\[")[1];
                uglate = uglate.split("]")[0];

                String[] znakoviUUglatima = uglate.split(",");

                String[] produkcija = (prijeOznakeTocke.trim() + " " + poslijeOznakeTocke.trim()).split(" ");

                if (poslijeOznakeTocke != null && !poslijeOznakeTocke.equals("") && !poslijeOznakeTocke.equals(" ") && !poslijeOznakeTocke.startsWith("<")) {
                    if(dka.getPrijelazi().get(state, poslijeOznakeTocke.split(" ")[0])!=null) {
                        for (StateSet tempState : dka.getPrijelazi().get(state, poslijeOznakeTocke.split(" ")[0])) {
                            actionTable.put(state.getStateName(), poslijeOznakeTocke.split(" ")[0], "P(" + tempState.getStateName() + ")");
                        }
                    }
                } else if (!lijevaStranaProdukcije.equals("<NovoPocetnoStanje>")  && (poslijeOznakeTocke == null || poslijeOznakeTocke.equals(" ") || poslijeOznakeTocke.equals(""))) {

                    for (int i = 0; i < znakoviUUglatima.length; i++) {
                        String action;
                        if(( action = actionTable.get(state.getStateName(),znakoviUUglatima[i]) ) != null) {
                            if (action.startsWith("P")) {
                                continue;
                            } else {
                                Production newProduction = new Production(lijevaStranaProdukcije, produkcija);
                                String sve = (action.split("\\(")[1]).split("\\)")[0];
                                Production oldProduction = new Production(sve.split("->")[0],(sve.split("->")[1].split(" ")));
                                for (Production element : storage.getModeledStorage()) {
                                    if (element.equals(oldProduction)) {
                                        break;
                                    } else if (element.equals(newProduction)) {
                                        if(prijeOznakeTocke.equals("")) {
                                            actionTable.put(state.getStateName().trim(), znakoviUUglatima[i].trim(), ("R(" + lijevaStranaProdukcije + "->" + "$" + ")").trim());
                                        } else {
                                            actionTable.put(state.getStateName().trim(), znakoviUUglatima[i].trim(), ("R(" + lijevaStranaProdukcije + "->" + prijeOznakeTocke + ")").trim());
                                        }
                                        break;
                                    }
                                }
                            }
                        } else {
                            if(prijeOznakeTocke.equals("")) {
                                actionTable.put(state.getStateName().trim(), znakoviUUglatima[i].trim(), ("R(" + lijevaStranaProdukcije + "->" + "$" + ")").trim());
                            } else {
                                actionTable.put(state.getStateName().trim(), znakoviUUglatima[i].trim(), ("R(" + lijevaStranaProdukcije + "->" + prijeOznakeTocke + ")").trim());
                            }
                        }
                    }

                } else if (lijevaStranaProdukcije.equals("<NovoPocetnoStanje>") && (poslijeOznakeTocke == null || poslijeOznakeTocke.equals(" ") || poslijeOznakeTocke.equals(""))) {
                    actionTable.put(state.getStateName().trim(), "OznakaKrajaNiza", "O()");
                }


            }
        }
        return actionTable;
    }

    public static DoubleMap<String, String, String> generateNewStateTable(DKA dka) {
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
