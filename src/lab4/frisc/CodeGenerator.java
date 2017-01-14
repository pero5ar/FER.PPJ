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
    private static boolean isNodeBROJ =false;
    private static boolean isNodeIDN =false;
    private static int nodeBROJ= 0;
    private static String nodeIDN = null;



    private List<Line> lines;

    private CodeGenerator() {
        subroutineManager = new SubroutineManager();
        variableManager = new VariableManager();
        startLines();
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

    public List<String> generateCode() {
        List<String> code = new ArrayList<>();
        lines.forEach(line -> code.add(line.toString()));
        variableManager.getAllVariableLines().forEach(line -> code.add(line.toString()));
        variableManager.getAllArrayLines().forEach(line -> code.add(line.toString()));
        return code;
    }

    public static boolean isNodeBROJ() {
        return isNodeBROJ;
    }

    public static void setIsNodeBROJ(boolean isNodeBROJ) {
        CodeGenerator.isNodeBROJ = isNodeBROJ;
    }

    public static boolean isNodeIDN() {
        return isNodeIDN;
    }

    public static void setIsNodeIDN(boolean isNodeIDN) {
        CodeGenerator.isNodeIDN = isNodeIDN;
    }

    public static int getNodeBROJ() {
        return nodeBROJ;
    }

    public static void setNodeBROJ(int nodeBROJ) {
        CodeGenerator.nodeBROJ = nodeBROJ;
    }

    public static String getNodeIDN() {
        return nodeIDN;
    }

    public static void setNodeIDN(String nodeIDN) {
        CodeGenerator.nodeIDN = nodeIDN;
    }

}
