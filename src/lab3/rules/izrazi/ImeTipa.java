package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.ConstType;
import lab3.types.IntType;
import lab3.types.VoidType;

public class ImeTipa extends Rule {
    public ImeTipa() {
        super("<ime_tipa>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        //dvije produkcije
        if (node.childSymbolEqual(0, Rules.SPECIFIKATOR_TIPA.symbol)) {
            check1(scope, node);
        } else if (
                node.childSymbolEqual(0, "KR_CONST") &&
                node.childSymbolEqual(1, Rules.SPECIFIKATOR_TIPA.symbol)
        ) {
            check2(scope, node);
        }
    }

    /**
     * <ime_tipa> ::= <specifikator_tipa>
     *
     * tip ← <specifikator_tipa>.tip
     *
     * 1. provjeri(<specifikator_tipa>)
     */
    private void check1(Scope scope, SemanticNode node) {
        // 1. provjeri(<specifikator_tipa>)
        SemanticNode specifikatorTipa = node.getChildAt(0);
        specifikatorTipa.check(scope);

        // spremi tip
        node.setType(specifikatorTipa.getType());
    }

    /**
     * <ime_tipa> ::= KR_CONST <specifikator_tipa>
     *
     * tip ← const(<specifikator_tipa>.tip)
     *
     * 1. provjeri(<specifikator_tipa>)
     * 2. <specifikator_tipa>.tip != void
     */
    private void check2(Scope scope, SemanticNode node) {
        // 1. provjeri(<specifikator_tipa>)
        SemanticNode specifikatorTipa = node.getChildAt(1);
        specifikatorTipa.check(scope);

        // 2. <specifikator_tipa>.tip != void
        if (specifikatorTipa.getType() instanceof VoidType) {
            throw new SemanticException(node.errorOutput(),
                    "const void not allowed.");
        }

        // spremi tip
        node.setType(
                (specifikatorTipa.getType() instanceof IntType)
                        ? ConstType.CONST_INT
                        : ConstType.CONST_CHAR
        );
    }
}
