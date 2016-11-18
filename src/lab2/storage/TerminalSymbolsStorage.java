package lab2.storage;

import common.InlineDataStorageSet;

/**
 * Used for storing terminal symbols. Each entry will appear only once.
 */
public class TerminalSymbolsStorage extends InlineDataStorageSet {

    private static final long serialVersionUID = -7347607160057867718L;

    public static final String LINE_START = "%T";

    public TerminalSymbolsStorage() {
        super();
    }
}
