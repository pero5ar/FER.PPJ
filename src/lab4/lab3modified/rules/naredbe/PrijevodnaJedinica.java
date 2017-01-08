package lab4.lab3modified.rules.naredbe;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;

public class PrijevodnaJedinica extends Rule {
    public PrijevodnaJedinica() {
        super("<prijevodna_jedinica>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        node.forEachChild(child -> child.check(scope));
    }
}
