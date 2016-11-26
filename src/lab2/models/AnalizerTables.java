package lab2.models;

import lab2.automaton.DKA;
import lab2.storage.ProductionRulesStorage;
import lab2.transform.TableGenerator;

import java.io.Serializable;

/**
 * Created by CHOPPER on 11/26/2016.
 */
public class AnalizerTables implements Serializable{

    private static final long serialVersionUID = 887695478852476157L;

    DoubleMap<String, String, String> newStateTable;
    DoubleMap<String, String, String> actionTable;

    public AnalizerTables(DKA dka, ProductionRulesStorage storage) {
        actionTable = TableGenerator.generateActionTable(dka, storage);
        newStateTable = TableGenerator.generateNewStateTable(dka);
    }

    public DoubleMap<String, String, String> getNewStateTable() {
        return newStateTable;
    }

    public DoubleMap<String, String, String> getActionTable() {
        return actionTable;
    }
}
