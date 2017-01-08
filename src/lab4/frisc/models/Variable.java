package lab4.frisc.models;

import lab4.lab3modified.models.Scope;

/**
 * ppjC variable
 *
 * Created by pero5ar on 7.1.2017..
 */
public class Variable {

    private Scope scope;
    private String name;

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
