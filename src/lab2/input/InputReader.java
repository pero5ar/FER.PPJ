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
                            Reader input)
    {
        try(BufferedReader reader = new BufferedReader(input)) {
            InlineDataStorage.readLineToStorage(reader.readLine(), nonterminalStorage, NonterminalSymbolsStorage.LINE_START);
            InlineDataStorage.readLineToStorage(reader.readLine(), terminalStorage, TerminalSymbolsStorage.LINE_START);
            InlineDataStorage.readLineToStorage(reader.readLine(), synchronizationStorage, SynchronizationSymbolsStorage.LINE_START);
            productionStorage.readToStorage(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
