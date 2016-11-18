package lab2.input;

import common.InlineDataStorage;
import lab2.storage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class InputReader {
    public static void read(NonterminalSymbolsStorage nonterminalStorage, TerminalSymbolsStorage terminalStorage,
                            SynchronizationSymbolsStorage synchronizationStorage, ProductionRulesStorage productionStorage,
                            Reader input) {
        try(BufferedReader reader = new BufferedReader(input)) {
            nonterminalStorage.readLine(reader.readLine(), NonterminalSymbolsStorage.LINE_START);
            terminalStorage.readLine(reader.readLine(), TerminalSymbolsStorage.LINE_START);
            synchronizationStorage.readLine(reader.readLine(), SynchronizationSymbolsStorage.LINE_START);
            productionStorage.readToStorage(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
