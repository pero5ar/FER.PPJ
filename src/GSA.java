import common.SerializableHelper;
import lab2.input.InputReader;
import lab2.storage.*;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class GSA {

    private static final String OUT_PATH = "";

    public static void main(String args[]) {
        NonterminalSymbolsStorage nonterminalStorage = new NonterminalSymbolsStorage();
        TerminalSymbolsStorage terminalStorage = new TerminalSymbolsStorage();
        SynchronizationSymbolsStorage synchronizationStorage = new SynchronizationSymbolsStorage();
        ProductionRulesStorage productionStorage = new ProductionRulesStorage();

        InputReader.read(nonterminalStorage, terminalStorage, synchronizationStorage, productionStorage);

        SerializableHelper.createOutput(OUT_PATH, nonterminalStorage, terminalStorage, synchronizationStorage, productionStorage);
    }
}
