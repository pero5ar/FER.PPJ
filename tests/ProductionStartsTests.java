import lab2.input.InputReader;
import lab2.storage.NonterminalSymbolsStorage;
import lab2.storage.ProductionRulesStorage;
import lab2.storage.SynchronizationSymbolsStorage;
import lab2.storage.TerminalSymbolsStorage;
import lab2.transform.ProductionStarts;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by pero5ar on 10.11.2016..
 */
public class ProductionStartsTests {

    private ProductionStarts productionStarts;

    public ProductionStartsTests() {
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

        productionStarts = new ProductionStarts(productionStorage, terminalStorage);
    }

    @Test
    public void generateStartsWithSymbolsMapTest() throws ReflectiveOperationException {
        Field startsDirectlyWithSymbols = productionStarts.getClass().getDeclaredField("startsDirectlyWithSymbols");
        startsDirectlyWithSymbols.setAccessible(true);

        Map<String, Set<String>> inputMap = new HashMap<>();
        inputMap.put("<A>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<B>", "b", "<C>"}
                )));
        inputMap.put("<B>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<C>", "b", "c"}
                )));
        inputMap.put("<C>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"b"}
                )));

        Method method = productionStarts.getClass().getDeclaredMethod("generateStartsWithSymbolsMap", Map.class);
        method.setAccessible(true);

        Map<String, Set<String>> returned = (Map<String, Set<String>>) method.invoke(productionStarts, inputMap);

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("<A>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<B>", "b", "<C>", "c"}
                )));
        expected.put("<B>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<C>", "b", "c"}
                )));
        expected.put("<C>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"b"}
                )));

        assertEquals(expected, returned);
    }

    /**
     * Test case by Nicky
     *
     * @throws Exception
     */
    @Test
    public void generateStartsWithSymbolsMapTest2() throws ReflectiveOperationException {
        Field startsDirectlyWithSymbols = productionStarts.getClass().getDeclaredField("startsDirectlyWithSymbols");
        startsDirectlyWithSymbols.setAccessible(true);

        Map<String, Set<String>> inputMap = new HashMap<>();
        inputMap.put("<A>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<B>", "<C>"}
                )));
        inputMap.put("<B>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<D>", "b", "a"}
                )));
        inputMap.put("<C>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"c"}
                )));
        inputMap.put("<D>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"d", "<E>"}
                )));
        inputMap.put("<E>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"e"}
                )));

        Method method = productionStarts.getClass().getDeclaredMethod("generateStartsWithSymbolsMap", Map.class);
        method.setAccessible(true);

        Map<String, Set<String>> returned = (Map<String, Set<String>>) method.invoke(productionStarts, inputMap);

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("<A>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<B>", "<C>", "<D>", "<E>", "b", "a", "c", "d", "e"}
                )));
        expected.put("<B>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<D>", "b", "a", "d", "<E>", "e"}
                )));
        expected.put("<C>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"c"}
                )));
        expected.put("<D>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"d", "<E>", "e"}
                )));
        expected.put("<E>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"e"}
                )));

        assertEquals(expected, returned);
    }

    @Test
    public void generateStartsWithTerminalSymbolsMapTest() throws ReflectiveOperationException {

        String[] t = {"b", "a", "c", "d", "e"};
        Set<String> terminalsSet = new HashSet<String>(Arrays.asList(t));

        Map<String, Set<String>> input = new HashMap<>();
        input.put("<A>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<B>", "<C>", "<D>", "<E>", "b", "a", "c", "d", "e"}
                )));
        input.put("<B>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"<D>", "b", "a", "d", "<E>", "e"}
                )));
        input.put("<C>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"c"}
                )));
        input.put("<D>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"d", "<E>", "e"}
                )));
        input.put("<E>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"e"}
                )));

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("<A>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"b", "a", "c", "d", "e"}
                )));
        expected.put("<B>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"b", "a", "d", "e"}
                )));
        expected.put("<C>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"c"}
                )));
        expected.put("<D>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"d", "e"}
                )));
        expected.put("<E>",
                new HashSet<String>(Arrays.asList(
                        new String[]{"e"}
                )));

        Method method = productionStarts.getClass().getDeclaredMethod("generateStartsWithTerminalSymbolsMap", Set.class, Map.class);
        method.setAccessible(true);

        Map<String, Set<String>> returned = (Map<String, Set<String>>) method.invoke(productionStarts, terminalsSet, input);

        assertEquals(expected, returned);
    }

}