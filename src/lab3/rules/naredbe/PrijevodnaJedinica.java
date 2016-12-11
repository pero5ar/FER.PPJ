package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;

public class PrijevodnaJedinica extends Rule {
    public PrijevodnaJedinica() {
        super("<prijevodna_jedinica>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        node.forEachChild(child -> child.check(scope));
    }
}
