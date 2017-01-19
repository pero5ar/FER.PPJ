package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;
import lab3.types.IntType;

/**
 * Naredba grananja i naredba petlje
 *
 * @author pero
 */
public abstract class SubscopeNaredba extends Rule {
    protected SubscopeNaredba(String symbol) {
        super(symbol);
    }

    /**
     * <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
     *
     * 1. provjeri(<izraz>)
     * 2. <izraz>.tip ~ int
     * 3. provjeri(<naredba>)
     */
    protected void check1(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(2);
        SemanticNode naredba = node.getChildAt(4);

        //1
        izraz.check(scope);
        //2
        SemanticHelper.assertTrue(
                izraz.getType().canImplicitCast(IntType.INSTANCE),
                new SemanticException(node.errorOutput())
        );
        //3
        naredba.check(new Scope(scope));
    }
}
