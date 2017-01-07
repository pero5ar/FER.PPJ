package lab4.frisc.managers;

import lab4.frisc.models.Line;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class SubroutineManager {

    private static int funcId = 0;
    private Map<String, Line> functions;

    public SubroutineManager() {
        functions = new HashMap<>();
    }

    public void createSubrutine(String name, String instruction) {
        funcId++;
        String label
    }
}
