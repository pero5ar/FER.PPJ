package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.IntType;

public class LogIliIzraz extends BoilerplateIzraz {
    public LogIliIzraz() {
        super("<log_ili_izraz>", Rules.LOG_I_IZRAZ.symbol);
    }
}
