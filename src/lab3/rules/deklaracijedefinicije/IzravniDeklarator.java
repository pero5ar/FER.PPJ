package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.semantic.SemanticException;
import lab3.types.VoidType;

/**
 * vidi: 4.4.6 (str. 69)
 */
public class IzravniDeklarator extends Rule {
    public IzravniDeklarator() {
        super("<izravni_deklarator>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.getChildren().size() == 1) {
            check1(scope, node);
            return;
        }

        throw new IllegalStateException("Not implemented.");
    }

    /**
     * <izravni_deklarator> ::= IDN
     *
     * tip ← ntip
     *
     * 1. ntip != void
     * 2. IDN.ime nije deklarirano u lokalnom djelokrugu
     * 3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
     */
    public void check1(Scope scope, SemanticNode node) {
        // 1. ntip != void
        if (node.getNType().equals(VoidType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 1. ntip != void");
        }

        // 2. IDN.ime nije deklarirano u lokalnom djelokrugu
        SemanticNode identifier = node.getChildAt(0);
        if (scope.isDeclared(identifier.getValue())) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 2. IDN.ime nije deklarirano u lokalnom djelokrugu");
        }

        // 3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
        scope.addElement(
                identifier.getValue(),
                new ScopeElement(node.getNType(), true)
        );

        node.setType(node.getNType());
    }
}
