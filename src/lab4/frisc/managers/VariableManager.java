package lab4.frisc.managers;

import lab4.frisc.models.Line;
import lab4.frisc.models.Variable;

import java.util.*;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class VariableManager {

    private static int varId = 0;
    private static int arrId = 0;
    private Map<Variable, Line> variables;
    private Map<Variable, List<Line>> arrays;

    public VariableManager() {
        variables = new HashMap<>();
        arrays = new HashMap<>();
    }

    public void createVariable(Variable variable, String instruction) {
        varId++;
        String label = "var" + Integer.toString(varId);
        Line line = new Line(label, instruction);
        variables.put(variable, line);
    }

    public String getVariableLabel(Variable variable) {
        if(variables.get(variable)!=null) {
            return variables.get(variable).getLabel();
        }
        return null;
    }

    public Collection<Line> getAllVariableLines() {
        return variables.values();
    }

    /**
     * Stvori niz tako da deklariras jednu varijablu (firstElement) da sluzi kao C-Ime i Scope,
     * navedes velicinu (size) i instrukciju za JEDAN element
     * (e.g. ako je "int a[10]", napravi varijablu i instrukciju kao za "int a", samo navedi i velicinu)
     *
     */
    public void createArray(Variable firstElement, String singleElementInstruction, int size) {
        arrId++;
        List<Line> lines = new ArrayList<>();
        for (int i=0; i < size; i++) {
            String label = "arr" + Integer.toString(arrId) + "at" + Integer.toString(i);
            lines.add(new Line(label, singleElementInstruction));
        }
        arrays.put(firstElement, lines);
    }

    public String getArrayElementAtLabel(Variable firstElement, int index) {
        return arrays.get(firstElement).get(index).getLabel();
    }

    public Collection<Line> getAllArrayLines() {
        Collection<Line> lines = new ArrayList<>();
        arrays.values().forEach(l -> lines.addAll(l));
        return lines;
    }
}
