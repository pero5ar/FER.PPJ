package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;

public class OdnosniIzraz extends BoilerplateIzraz {
    public OdnosniIzraz() {
        super("<odnosni_izraz>", Rules.ADITIVNI_IZRAZ.symbol);
    }
}
