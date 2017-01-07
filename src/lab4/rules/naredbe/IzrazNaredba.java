package lab4.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.types.IntType;
import lab4.rules.Rule;

public class IzrazNaredba extends Rule {
    public IzrazNaredba() {
        super("<izraz_naredba>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, "TOCKAZAREZ")) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <izraz_naredba> ::= TOCKAZAREZ
     *
     * tip <- int
     */
    private void check1(Scope scope, SemanticNode node) {
        node.setType(IntType.INSTANCE);
    }

    /**
     * <izraz_naredba> ::= <izraz> TOCKAZAREZ
     *
     * tip <- <izraz>.tip
     *
     * 1. provjeri(<izraz>)
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(0);

        // 1. provjeri(<izraz>)
        izraz.check(scope);

        node.setType(izraz.getType());
    }

}
