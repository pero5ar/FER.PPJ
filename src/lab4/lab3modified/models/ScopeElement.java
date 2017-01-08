package lab4.lab3modified.models;

import lab4.lab3modified.types.Type;

public class ScopeElement {
    private Type type;

    /**
     * False if symbol is just declared but not defined. True otherwise.
     */
    public boolean defined = false;

    public ScopeElement(Type type, boolean defined) {
        this.type = type;
        this.defined = defined;
    }

    public Type getType() {
        return type;
    }
}
