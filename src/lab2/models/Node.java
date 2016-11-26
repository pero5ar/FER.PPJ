package lab2.models;

import java.util.ArrayList;

/**
 * Created by CHOPPER on 11/26/2016.
 */
public class Node {

    private AnalizerInput value;
    private String name;
    private ArrayList<Node> children;

    public Node(String name, AnalizerInput value) {
        this.name = name;
        this.value = value;
        this.children = new ArrayList<Node>();
    }

    public void setNode(Node node) {
        this.name = node.getName();
        this.value = node.getValue();
        this.children = node.getChildren();
    }

    public AnalizerInput getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(0, child);
    }

    public String printValue() {
        return " " + value.getRedak() + " " + value.getKlasa();
    }
}
