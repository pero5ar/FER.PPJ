package lab2.tests;

import common.InlineDataStorage;
import lab2.input.InputReader;
import lab2.storage.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class TestInputReader {

    public static void main(String[] args) {
        NonterminalSymbolsStorage nonterminalStorage = new NonterminalSymbolsStorage();
        TerminalSymbolsStorage terminalStorage = new TerminalSymbolsStorage();
        SynchronizationSymbolsStorage synchronizationStorage = new SynchronizationSymbolsStorage();
        ProductionRulesStorage productionStorage = new ProductionRulesStorage();

        try {
            InputReader.read(nonterminalStorage, terminalStorage, synchronizationStorage, productionStorage,
                    new FileReader("src/lab2/tests/testFile"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //output
        /*
        printInlineDataStorage(nonterminalStorage);
        printInlineDataStorage(terminalStorage);
        printInlineDataStorage(synchronizationStorage);
        printProductionRulesStorage(productionStorage);
        */
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
