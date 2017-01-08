package lab4.lab3modified.rules.izrazi;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.types.CharType;
import lab4.lab3modified.types.IntType;
import lab4.lab3modified.types.VoidType;

/**
 * Sluzi samo za postavljanje tipa; ne moze doci do semanticke pogreske.
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
