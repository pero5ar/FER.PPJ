package lab2.storage;

import common.InlineDataStorageSet;

/**
 * Used for storing non-terminal symbols. Each entry will appear only once.
 */
public class NonterminalSymbolsStorage extends InlineDataStorageSet {

    private static final long serialVersionUID = 7976121188984433179L;

    public static final String LINE_START = "%V";

    public NonterminalSymbolsStorage() {
        super();
    }
}
