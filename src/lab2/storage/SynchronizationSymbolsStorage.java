package lab2.storage;

import common.InlineDataStorageSet;

/**
 * Used for storing synchonization symbols. Each entry will appear only once.
 */
public class SynchronizationSymbolsStorage extends InlineDataStorageSet {

    private static final long serialVersionUID = 4743420475511860073L;

    public static final String LINE_START = "%Syn";

    public SynchronizationSymbolsStorage() {
        super();
    }
}
