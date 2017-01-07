package lab4.frisc;

import lab4.frisc.managers.SubroutineManager;
import lab4.frisc.managers.VariableManager;
import lab4.frisc.models.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class CodeGenerator {

    private static CodeGenerator instance = null;

    private SubroutineManager subroutineManager;
    private VariableManager variableManager;

    private List<Line> lines;

    private CodeGenerator() {
        subroutineManager = new SubroutineManager();
        variableManager = new VariableManager();
    }

    public static CodeGenerator getInstance() {
        if (instance == null) instance = new CodeGenerator();
        return instance;
    }

    public SubroutineManager getSubroutineManager() {
        return subroutineManager;
    }

    public VariableManager getVariableManager() {
        return variableManager;
    }

    public List<Line> getLines() {
        return lines;
    }

    private void startLines() {
        lines = new ArrayList<>();
        lines.add(new Line("MOVE 40000, R7"));
        lines.add(new Line("CALL F_MAIN"));
        lines.add(new Line("HALT"));
    }

}
