import common.SerializableHelper;
import lab2.automaton.DKA;
import lab2.automaton.EpsilonNKA;
import lab2.input.InputReader;
import lab2.models.AnalizerTables;
import lab2.storage.*;
import lab2.transform.AutomataGenerator;
import lab2.transform.ProductionStarts;

import java.io.InputStreamReader;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class GSA {

    private static final String OUT_PATH = "analizator/storage.bin";
    private static NonterminalSymbolsStorage nonterminalStorage;
    private static TerminalSymbolsStorage terminalStorage;
    private static SynchronizationSymbolsStorage synchronizationStorage;
    private static ProductionRulesStorage productionStorage;



    public static void main(String args[]) {
        NonterminalSymbolsStorage nonterminalStorage = new NonterminalSymbolsStorage();
        TerminalSymbolsStorage terminalStorage = new TerminalSymbolsStorage();
        SynchronizationSymbolsStorage synchronizationStorage = new SynchronizationSymbolsStorage();
        ProductionRulesStorage productionStorage = new ProductionRulesStorage();

        InputReader.read(nonterminalStorage, terminalStorage, synchronizationStorage, productionStorage,
                        new InputStreamReader(System.in));

        AnalizerTables tables = buildTables();

        SerializableHelper.createOutput(OUT_PATH, synchronizationStorage, tables);
    }

    public static AnalizerTables buildTables() {
        ProductionStarts productionStarts = new ProductionStarts(productionStorage, terminalStorage, nonterminalStorage);
        EpsilonNKA eNKA = AutomataGenerator.generirajENKA(
                AutomataGenerator.generirajStavke(productionStorage.getModeledStorage()),
                terminalStorage.getTypedStorage(),
                productionStarts.getEmptyNonterminalSymbols(),
                productionStarts.getStartsWithTerminalSymbols(),
                productionStarts.getPocetnoStanje()
        );
        DKA dka = DKA.fromEpsilonNKA(eNKA);
        dka = AutomataGenerator.generirajBrojeveStanja(dka);
        return new AnalizerTables(dka, productionStorage);
    }
}
