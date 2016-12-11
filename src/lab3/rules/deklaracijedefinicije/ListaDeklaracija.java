package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class ListaDeklaracija extends Rule {
    public ListaDeklaracija() {
        super("<lista_deklaracija>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.DEKLARACIJA.symbol)) {
            check1(scope, node);
        } else if (node.childSymbolEqual(0, Rules.LISTA_DEKLARACIJA.symbol) &&
                node.childSymbolEqual(1, Rules.DEKLARACIJA.symbol)) {
            check2(scope, node);
        }
    }

    /**
     * <lista_deklaracija> ::= <deklaracija>
     *
     * 1. provjeri(<deklaracija>)
     */
    private void check1(Scope scope, SemanticNode node) {
        // 1. provjeri(<deklaracija>)
        node.getChildAt(0).check(scope);
    }

    /**
     * <lista_deklaracija> ::= <lista_deklaracija> <deklaracija>
     *
     * 1. provjeri(<lista_deklaracija>)
     * 2. provjeri(<deklaracija>)
     */
    private void check2(Scope scope, SemanticNode node) {
        // 1. provjeri(<lista_deklaracija>)
        node.getChildAt(0).check(scope);

        // 2. provjeri(<deklaracija>)
        node.getChildAt(1).check(scope);
    }


}
