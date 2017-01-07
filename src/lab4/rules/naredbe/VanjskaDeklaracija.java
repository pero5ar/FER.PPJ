package lab4.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab4.rules.Rule;

public class VanjskaDeklaracija extends Rule {
    public VanjskaDeklaracija() {
        super("<vanjska_deklaracija>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        node.forEachChild(child -> child.check(scope));
    }
}
