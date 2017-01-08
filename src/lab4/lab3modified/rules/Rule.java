package lab4.lab3modified.rules;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;

public abstract class Rule {
    private static final long serialVersionUID = -5404952418610448942L;

    public final String symbol;

    public Rule(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Verifies the node against this rule in the given scope.
     */
    public abstract void check(Scope scope, SemanticNode node);
}
