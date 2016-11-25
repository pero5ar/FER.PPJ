package lab2.models;

/**
 * Created by CHOPPER on 11/25/2016.
 */
public class Stablo {

    private String parent;
    private String child;
    private String leaf;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public Stablo(String parent, String child, String leaf) {
        this.parent = parent;
        this.child = child;
        this.leaf = leaf;
    }
}
