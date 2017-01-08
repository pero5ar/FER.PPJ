package lab4.lab3modified.rules.naredbe;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.IntType;

/**
 * Naredba grananja i naredba petlje
 *
 * @author JJ
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
