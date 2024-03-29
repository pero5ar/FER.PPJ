package lab4.lab3modified.rules.naredbe;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.types.IntType;

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
