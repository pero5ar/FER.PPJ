package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.types.CharType;
import lab3.types.IntType;
import lab3.types.VoidType;

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
