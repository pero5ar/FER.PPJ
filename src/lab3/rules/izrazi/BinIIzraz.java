package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;

public class BinIIzraz extends BoilerplateIzraz {
    public BinIIzraz() {
        super("<bin_i_izraz>", Rules.JEDNAKOSNI_IZRAZ.symbol);
    }
}
