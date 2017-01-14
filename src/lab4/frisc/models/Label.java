package lab4.frisc.models;

import lab4.frisc.CodeGenerator;

public class Label {
    private String value;
    private int id;

    public Label() {
        this.id = CodeGenerator.getInstance().theLabelCounter();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        // const label
        return "cl" + id;
    }

    public String getValue() {
        return value;
    }

    public String getBytes() {
        String word = "DW %D ";

        int intValue;
        //char or int
        if (value.startsWith("'")) {
            intValue = (int) value.charAt(1);
        } else {
            intValue = Integer.parseInt(value);
        }

        return word + intValue;
    }
}
