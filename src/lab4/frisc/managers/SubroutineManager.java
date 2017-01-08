package lab4.frisc.managers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class SubroutineManager {

    private static int funcId = 0;
    private Map<String, String> functions;  // ppjC function name => FRISC subroutine label

    public SubroutineManager() {
        functions = new HashMap<>();
    }

    public String createSubrutine(String name) {
        funcId++;
        String label = "func" + Integer.toString(funcId);
        functions.put(name, label);
        return label;
    }

    public String getSubrutineLabel(String name) {
        return functions.get(name);
    }
}
