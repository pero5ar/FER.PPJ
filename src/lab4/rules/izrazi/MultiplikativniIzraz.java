package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rules;

public class MultiplikativniIzraz extends BoilerplateIzraz {
    public MultiplikativniIzraz() {
        super("<multiplikativni_izraz>", Rules.CAST_IZRAZ.symbol);
    }

    protected void check1(Scope scope, SemanticNode node) {
        super.check1(scope, node);
    }
}
