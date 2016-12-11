package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.*;

public class PrimarniIzraz extends Rule {
    public PrimarniIzraz() {
        super("<primarni_izraz>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        SemanticNode child = node.getChildAt(0);

        boolean result = false;
        switch(child.getSymbol()) {
            case "IDN":
                result = checkIdentifier(scope, child);
                break;
            case "BROJ":
                result = checkInt(child);
                break;
            case "ZNAK":
                result = checkChar(child);
                break;
            case "NIZ_ZNAKOVA":
                result = checkCharArray(child);
                break;
            case "L_ZAGRADA":
                if (node.childSymbolEqual(1, Rules.IZRAZ.symbol) &&
                        node.childSymbolEqual(2, "D_ZAGRADA")) {
                    result = checkIzraz(scope, node.getChildAt(1));
                }
                break;
        }

        if (!result) {
            throw new SemanticException(node.errorOutput(),
                    "Expression error (<primarni_izraz>)");
        }
    }

    /**
     * <primarni_izraz> ::= IDN
     *
     * tip ← IDN.tip
     * l-izraz ← IDN.l-izraz
     *
     * 1. IDN.ime je deklarirano
     */
    private boolean checkIdentifier(Scope scope, SemanticNode node) {
        ScopeElement element = scope.getElement(node.getValue());
        if (element == null) {
            return false;
        }

        node.setType(element.getType());
        node.setLValue(element.getType() instanceof NumberType);

        return true;
    }

    /**
     * <primarni_izraz> ::= BROJ
     *
     * tip ← int
     * l-izraz ← 0
     *
     * 1. vrijednost je u rasponu tipa int
     */
    private boolean checkInt(SemanticNode node) {
        node.getChildAt(0).setType(IntType.INSTANCE);
        node.setLValue(false);

        return IntType.INSTANCE.validRange(node.getValue());
    }

    /**
     * <primarni_izraz> ::= ZNAK
     *
     * tip ← char
     * l-izraz ← 0
     *
     * 1. znak je ispravan po 4.3.2
     */
    private boolean checkChar(SemanticNode node) {
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        return CharType.INSTANCE.validRange(node.getValue());
    }

    /**
     * <primarni_izraz> ::= NIZ_ZNAKOVA
     *
     * tip ← niz(const(char))
     * l-izraz ← 0
     *
     * 1. konstantni niz znakova je ispravan po 4.3.2
     */
    private boolean checkCharArray(SemanticNode node) {
        node.setType(new ArrayType(ConstType.CONST_CHAR));
        node.setLValue(false);

        return ArrayType.validString(node.getValue());
    }

    /**
     * <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
     *
     * tip ← <izraz>.tip
     * l-izraz ← <izraz>.l-izraz
     *
     * 1. provjeri(<izraz>)
     */
    private boolean checkIzraz(Scope scope, SemanticNode node) {
        node.check(scope);

        node.setType(node.getType());
        node.setLValue(node.isLValue());

        return true;
    }
}
