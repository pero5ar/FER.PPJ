package lab4.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab4.rules.Rule;
import lab4.rules.Rules;

/**
 * vidi: 4.4.5 (str. 62)
 *
 * @author JJ
 */
public class SlozenaNaredba extends Rule {
    public SlozenaNaredba() {
        super("<slozena_naredba>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (!node.getChildAt(0).getSymbol().equals("L_VIT_ZAGRADA")) {
            throw new RuntimeException("Expected L_VIT_ZAGRADA.");
        }

        if (node.childSymbolEqual(1, Rules.LISTA_NAREDBI.symbol) &&
                node.childSymbolEqual(2, "D_VIT_ZAGRADA")) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
     *
     * 1. provjeri(<lista_naredbi>)
     */
    private void check1(Scope scope, SemanticNode node) {
        // 1. provjeri(<lista_naredbi>)
        node.getChildAt(1).check(scope);
    }

    /**
     * <slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija>
     * <lista_naredbi> D_VIT_ZAGRADA
     *
     * 1. provjeri(<lista_deklaracija>)
     * 2. provjeri(<lista_naredbi>)
     */
    private void check2(Scope scope, SemanticNode node) {
        // 1. provjeri(<lista_deklaracija>)
        node.getChildAt(1).check(scope);

        // 2. provjeri(<lista_naredbi>)
        node.getChildAt(2).check(scope);
    }
}
