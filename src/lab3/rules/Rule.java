package lab3.rules;

import lab3.models.Scope;
import lab3.models.SemanticNode;

public abstract class Rule {
    public final String symbol;

    public Rule(String symbol) {
        this.symbol = symbol;
    }

    public void apply(Scope scope, SemanticNode node) {
        node.setScope(scope);
        check(scope, node);
    }

    /**
     * Verifies the node against this rule in the given scope.
     * @param scope
     * @param node
     */
    public abstract void check(Scope scope, SemanticNode node);
}
