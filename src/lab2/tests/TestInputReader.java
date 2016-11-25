package lab2.tests;

import common.InlineDataStorage;
import lab2.automaton.Automaton;
import lab2.automaton.EpsilonNKA;
import lab2.input.InputReader;
import lab2.storage.*;
import lab2.transform.AutomataGenerator;
import lab2.transform.ProductionStarts;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class TestInputReader {

    private static NonterminalSymbolsStorage nonterminalStorage = new NonterminalSymbolsStorage();
    private static TerminalSymbolsStorage terminalStorage = new TerminalSymbolsStorage();
    private static SynchronizationSymbolsStorage synchronizationStorage = new SynchronizationSymbolsStorage();
    private static ProductionRulesStorage productionStorage = new ProductionRulesStorage();

    public static void main(String[] args) {
        try {
            InputReader.read(nonterminalStorage, terminalStorage, synchronizationStorage, productionStorage,
                    new FileReader("src/lab2/tests/testFile"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        transformLogic();
        //output
        /*
        printInlineDataStorage(nonterminalStorage);
        printInlineDataStorage(terminalStorage);
        printInlineDataStorage(synchronizationStorage);
        printProductionRulesStorage(productionStorage);
        */
    }

    public static void transformLogic() {
        ProductionStarts productionStarts = new ProductionStarts(productionStorage, terminalStorage, nonterminalStorage);
        EpsilonNKA eNKA = AutomataGenerator.generirajENKA(
                AutomataGenerator.generirajStavke(productionStorage.getModeledStorage()),
                terminalStorage.getTypedStorage(),
                productionStarts.getEmptyNonterminalSymbols(),
                productionStarts.getStartsWithTerminalSymbols(),
                productionStarts.getPocetnoStanje()
        );
         eNKA.ispisPrijelaza();
    }


    private static void printInlineDataStorage(InlineDataStorage storage) {
        storage.getStorage().forEach(s -> System.out.print(s + " "));
        System.out.println();
        System.out.println();
    }

    private static void printProductionRulesStorage(ProductionRulesStorage storage) {
        for (Map.Entry<String, List<String[]>> entry : storage.getStorage().entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(a -> System.out.println(Arrays.toString(a)));
            System.out.println();
        }
    }
}
