package lab4.frisc.managers;

import lab4.frisc.models.Line;
import lab4.frisc.models.Variable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class VariableManager {

    private static int varId = 0;
    private Map<Variable, Line> variables;

    public VariableManager() {
        variables = new HashMap<>();
    }

    public void createVariable(Variable variable, String instruction) {
        varId++;
        String label = "var" + Integer.toString(varId);
        Line line = new Line(label, instruction);
        variables.put(variable, line);
    }

    public String getVariableLabel(Variable variable) {
        return variables.get(variable).getLabel();
    }

    public Collection<Line> getAllLines() {
        return variables.values();
    }
}
