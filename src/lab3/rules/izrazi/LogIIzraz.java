package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.IntType;

public class LogIIzraz extends BoilerplateIzraz {
    public LogIIzraz() {
        super("<log_i_izraz>", Rules.BIN_ILI_IZRAZ.symbol);
    }
}
