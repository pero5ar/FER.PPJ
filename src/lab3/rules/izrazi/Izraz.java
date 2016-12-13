package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;

public class Izraz extends BoilerplateIzraz {
    public Izraz() {
        super("<izraz>", Rules.IZRAZ_PRIDRUZIVANJA.symbol);
    }

    protected void check1(Scope scope, SemanticNode node) {
        super.check1(scope, node);
    }

    /**
     * <izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>
     *
     * tip ← <izraz_pridruzivanja>.tip
     * l-izraz ← 0
     *
     * 1. provjeri(<izraz>)
     * 2. provjeri(<izraz_pridruzivanja>)
     */
    @Override
    protected void check2(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(0);
        SemanticNode izrazPridruzivanja = node.getChildAt(2);

        node.setType(izrazPridruzivanja.getType());
        node.setLValue(false);

        izraz.check(scope);
        izrazPridruzivanja.check(scope);
    }
}
