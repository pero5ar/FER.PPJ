package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;

public class JednakosniIzraz extends BoilerplateIzraz {
    public JednakosniIzraz() {
        super("<jednakosni_izraz>", Rules.ODNOSNI_IZRAZ.symbol);
    }
}
