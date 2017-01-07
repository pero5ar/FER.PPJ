package lab4.rules.naredbe;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.types.IntType;

/**
 * Služi samo za postavljanje tipa; ne može doći do semantičke pogreške.
 */
public class IzrazNaredba extends Rule {
    public IzrazNaredba() {
        super("<specifikator_tipa>");
    }


    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, "TOCKAZAREZ")) {
            check1(scope, node);
        } else {
            check2(scope, node);
        }
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
