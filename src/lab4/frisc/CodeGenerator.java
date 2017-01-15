package lab4.frisc;

import lab4.frisc.managers.SubroutineManager;
import lab4.frisc.managers.VariableManager;
import lab4.frisc.models.Label;
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
    private static boolean isNodeUnarniMinus=false;
    private static boolean isNodeIDN =false;
    private static boolean isNodeIzraz=false;



    private static boolean isZnak=false;
    private static boolean isNodeKR_INT=false;
    private static boolean isNodeListaInitDeklaratora=false;
    private static ArrayList<String> nodeListaInitDeklaratora=new ArrayList<String>();
    private static int nodeBROJ= 0;
    private static String nodeIDN = null;



    private List<Line> lines;
    private List<Label> labels = new ArrayList<>();

    private int labelCounter = 1;

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

    public int theLabelCounter() {
        return labelCounter++;
    }

    public List<String> generateCode() {
        List<String> code = new ArrayList<>();
        lines.forEach(line -> code.add(line.toString()));
        variableManager.getAllVariableLines().forEach(line -> code.add(line.toString()));
        variableManager.getAllArrayLines().forEach(line -> code.add(line.toString()));

        labels.forEach(l -> {
            String line = l.getLabel() + "\t" + l.getBytes();

            code.add(line);
        });

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

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public static boolean isNodeKR_INT() {
        return isNodeKR_INT;
    }

    public static void setIsNodeKR_INT(boolean isNodeKR_INT) {
        CodeGenerator.isNodeKR_INT = isNodeKR_INT;
    }

    public static boolean isNodeListaInitDeklaratora() {
        return isNodeListaInitDeklaratora;
    }

    public static void setIsNodeListaInitDeklaratora(boolean isNodeListaInitDeklaratora) {
        CodeGenerator.isNodeListaInitDeklaratora = isNodeListaInitDeklaratora;
    }

    public static ArrayList<String> getNodeListaInitDeklaratora() {
        return nodeListaInitDeklaratora;
    }

    public static void setNodeListaInitDeklaratora(ArrayList<String> nodeListaInitDeklaratora) {
        CodeGenerator.nodeListaInitDeklaratora = nodeListaInitDeklaratora;
    }

    public static boolean isIsNodeUnarniMinus() {
        return isNodeUnarniMinus;
    }

    public static void setIsNodeUnarniMinus(boolean isNodeUnarniMinus) {
        CodeGenerator.isNodeUnarniMinus = isNodeUnarniMinus;
    }

    public static boolean isNodeIzraz() {
        return isNodeIzraz;
    }

    public static void setIsNodeIzraz(boolean isNodeIzraz) {
        CodeGenerator.isNodeIzraz = isNodeIzraz;
    }

    public static boolean isIsZnak() {
        return isZnak;
    }

    public static void setIsZnak(boolean isZnak) {
        CodeGenerator.isZnak = isZnak;
    }
}
