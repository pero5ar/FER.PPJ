package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.types.CharType;
import lab4.types.IntType;
import lab4.types.VoidType;

/**
 * Služi samo za postavljanje tipa; ne može doći do semantičke pogreške.
 */
public class SpecifikatorTipa extends Rule {
    public SpecifikatorTipa() {
        super("<specifikator_tipa>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        // samo postavljanje tipova
        // KR_VOID -> void
        // KR_CHAR -> char
        // KR_INT -> int

        switch (node.getChildAt(0).getSymbol()) {
            case "KR_VOID":
                node.setType(VoidType.INSTANCE);
                break;
            case "KR_CHAR":
                node.setType(CharType.INSTANCE);
                break;
            case "KR_INT":
                node.setType(IntType.INSTANCE);
                break;
            default:
                throw new RuntimeException("FATAL ERROR - unexpected unknown type. Should not have happened (see pdf page 56)");
        }
    }

}
