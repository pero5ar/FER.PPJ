package lab4.frisc.models;

import lab3.models.Scope;

/**
 * ppjC variable
 *
 * Created by pero5ar on 7.1.2017..
 */
public class Variable {

    private Scope scope;
    private String name;

    private String type;
    private String value;

    public Variable(Scope scope, String name, String type, String value) {
        this.scope = scope;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Variable(Scope scope, String name) {
        this.scope = scope;
        this.name = name;
    }

    public Scope getScope() {
        return scope;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable = (Variable) o;

        if (!scope.equals(variable.scope)) return false;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        int result = scope.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

}
