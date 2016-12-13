package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;

public class IzrazPridruzivanja extends BoilerplateIzraz {
    public IzrazPridruzivanja() {
        super("<izraz_pridruzivanja>", Rules.LOG_ILI_IZRAZ.symbol);
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

        // 1. provjeri(<postfiks_izraz>)
        postfiksIzraz.check(scope);

        // 2. <postfiks_izraz>.l-izraz = 1
        SemanticHelper.assertTrue(
                postfiksIzraz.isLValue(),
                new SemanticException(node.errorOutput(), "Must be l-value")
        );

        // 3. provjeri(<izraz_pridruzivanja>)
        SemanticNode izrazPridruzivanja = node.getChildAt(2);
        izrazPridruzivanja.check(scope);

        // 4. <izraz_pridruzivanja>.tip ∼ <postfiks_izraz>.tip
        SemanticHelper.assertTrue(
                izrazPridruzivanja.getType().canImplicitCast(postfiksIzraz.getType()),
                new SemanticException(node.errorOutput(), "Rule broken: 4. <izraz_pridruzivanja>.tip ∼ <postfiks_izraz>.tip")
        );

        // tip ← <postfiks_izraz>.tip && l-izraz ← 0
        node.setType(postfiksIzraz.getType());
        node.setLValue(false);
    }
}
