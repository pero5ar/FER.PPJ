package lab4.lab3modified.rules.naredbe;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;

public class VanjskaDeklaracija extends Rule {
    public VanjskaDeklaracija() {
        super("<vanjska_deklaracija>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        node.forEachChild(child -> child.check(scope));
    }
}
