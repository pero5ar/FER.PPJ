package lab4.lab3modified.rules.naredbe;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.rules.Rules;

/**
 * vidi: 4.4.5 (str. 62)
 *
 * @author JJ
 */
public class ListaNaredbi extends Rule {
    public ListaNaredbi() {
        super("<lista_naredbi>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.NAREDBA.symbol)) {
            check1(scope, node);
        } else if (node.childSymbolEqual(0, Rules.LISTA_NAREDBI.symbol) &&
                node.childSymbolEqual(1, Rules.NAREDBA.symbol)) {
            check2(scope, node);
        }
    }

    /**
     * <lista_naredbi> ::= <naredba>
     *
     * 1. provjeri(<naredba>)
     */
    private void check1(Scope scope, SemanticNode node) {
        // 1. provjeri(<naredba>)
        node.getChildAt(0).check(scope);
    }

    /**
     * <lista_naredbi> ::= <lista_naredbi> <naredba>
     *
     * 1. provjeri(<lista_naredbi>)
     * 2. provjeri(<naredba>)
     */
    private void check2(Scope scope, SemanticNode node) {
        // 1. provjeri(<lista_naredbi>)
        node.getChildAt(0).check(scope);

        // 2. provjeri(<naredba>)
        node.getChildAt(1).check(scope);
    }
}
