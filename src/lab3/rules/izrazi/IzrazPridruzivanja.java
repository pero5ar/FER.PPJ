package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;

public class IzrazPridruzivanja extends BoilerplateIzraz {
    public IzrazPridruzivanja() {
        super("<izraz_pridruzivanja>", Rules.LOG_ILI_IZRAZ.symbol);
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        super.check(scope, node);
    }

    /**
     * <izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>
     *
     * tip ← <postfiks_izraz>.tip
     * l-izraz ← 0
     *
     * 1. provjeri(<postfiks_izraz>)
     * 2. <postfiks_izraz>.l-izraz = 1
     * 3. provjeri(<izraz_pridruzivanja>)
     * 4. <izraz_pridruzivanja>.tip ∼ <postfiks_izraz>.tip
     */
    @Override
    protected void check2(Scope scope, SemanticNode node) {
        SemanticNode postfiksIzraz = node.getChildAt(0);
        SemanticNode izrazPridruzivanja = node.getChildAt(2);

        // 1. provjeri(<postfiks_izraz>)
        postfiksIzraz.check(scope);

        // 2. <postfiks_izraz>.l-izraz = 1
        if (!postfiksIzraz.isLValue()) {
            throw new SemanticException(node.errorOutput(),
                    "Must be l-value");
        }

        // 3. provjeri(<izraz_pridruzivanja>)
        izrazPridruzivanja.check(scope);

        // 4. <izraz_pridruzivanja>.tip ∼ <postfiks_izraz>.tip
        if (!izrazPridruzivanja.getType().canImplicitCast(postfiksIzraz.getType())) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 4. <izraz_pridruzivanja>.tip ∼ <postfiks_izraz>.tip");
        }

        // tip ← <postfiks_izraz>.tip && l-izraz ← 0
        node.setType(postfiksIzraz.getType());
        node.setLValue(false);
    }
}
