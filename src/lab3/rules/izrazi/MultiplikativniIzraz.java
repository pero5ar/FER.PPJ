package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;

public class MultiplikativniIzraz extends BoilerplateIzraz {
    public MultiplikativniIzraz() {
        super("<multiplikativni_izraz>", Rules.CAST_IZRAZ.symbol);
    }
}
