package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;

/**
 * vidi: 4.4.5 (str. 62)
 *
 * @author JJ
 */
public class Naredba extends Rule {
    public Naredba() {
        super("<naredba>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.SLOZENA_NAREDBA.symbol)) {
            // <slozena_naredba> - stvori novi djelokrug
            node.getChildAt(0).check(new Scope(scope));
        } else {
            // <izraz_naredba>, <naredba_grananja>, <naredba_petlje> i <naredba_skoka>
            node.getChildAt(0).check(scope);
        }
    }
}
