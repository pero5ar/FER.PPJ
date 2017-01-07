package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.ScopeElement;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.semantic.SemanticException;
import lab4.types.*;

public class PrimarniIzraz extends Rule {
    public PrimarniIzraz() {
        super("<primarni_izraz>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        SemanticNode child = node.getChildAt(0);

        switch(child.getSymbol()) {
            case "IDN":
                checkIdentifier(scope, node);
                break;
            case "BROJ":
                checkInt(node);
                break;
            case "ZNAK":
                checkChar(node);
                break;
            case "NIZ_ZNAKOVA":
                checkCharArray(node);
                break;
            case "L_ZAGRADA":
//                if (node.childSymbolEqual(1, Rules.IZRAZ.symbol) &&
//                        node.childSymbolEqual(2, "D_ZAGRADA")) {
                    checkIzraz(scope, node.getChildAt(1));
//                }
                break;
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
    private void checkIdentifier(Scope scope, SemanticNode node) {
        SemanticNode child = node.getChildAt(0);

        ScopeElement element = scope.getElement(child.getValue(), true);
        if (!scope.isDeclared(child.getValue(), true)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 1. IDN.ime je deklarirano");
        }

        node.setType(element.getType());
        node.setLValue(element.getType() instanceof NumberType);
    }

    /**
     * <primarni_izraz> ::= BROJ
     *
     * tip ← int
     * l-izraz ← 0
     *
     * 1. vrijednost je u rasponu tipa int
     */
    private void checkInt(SemanticNode node) {
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        if (!IntType.INSTANCE.validRange(node.getChildAt(0).getValue())) {
            throw new SemanticException(node.errorOutput());
        }
    }

    /**
     * <primarni_izraz> ::= ZNAK
     *
     * tip ← char
     * l-izraz ← 0
     *
     * 1. znak je ispravan po 4.3.2
     */
    private void checkChar(SemanticNode node) {
        node.setType(CharType.INSTANCE);
        node.setLValue(false);

        if (!CharType.INSTANCE.validRange(node.getChildAt(0).getValue())) {
            throw new SemanticException(node.errorOutput());
        }
    }

    /**
     * <primarni_izraz> ::= NIZ_ZNAKOVA
     *
     * tip ← niz(const(char))
     * l-izraz ← 0
     *
     * 1. konstantni niz znakova je ispravan po 4.3.2
     */
    private void checkCharArray(SemanticNode node) {
        node.setType(new ArrayType(ConstType.CONST_CHAR));
        node.setLValue(false);

        if (!ArrayType.validString(node.getChildAt(0).getValue())) {
            throw new SemanticException(node.errorOutput());
        }
    }

    /**
     * <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
     *
     * tip ← <izraz>.tip
     * l-izraz ← <izraz>.l-izraz
     *
     * 1. provjeri(<izraz>)
     */
    private void checkIzraz(Scope scope, SemanticNode node) {
        node.check(scope);

        node.setType(node.getType());
        node.setLValue(node.isLValue());
    }
}
