package lab4.frisc.models;

/**
 * FRISC instruction line
 *
 * Created by pero5ar on 7.1.2017..
 */
public class Line {

    private static int labelId = 0;

    private String label;
    private String instruction;

    public Line(String instruction) {
        labelId++;
        this.label = "lab" + Integer.toString(labelId);
        this.instruction = instruction;
    }

    public Line(String label, String instruction) {
        labelId++;
        this.label = label;
        this.instruction = instruction;
    }

    public String getLabel() {
        return label;
    }

    public String getInstruction() {
        return instruction;
    }

    @Override
    public String toString() {
        return label + "\t" + instruction;
    }
}
