package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;
import lab3.types.*;

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
                return;
            case "BROJ":
                checkInt(node);
                return;
            case "ZNAK":
                checkChar(node);
                return;
            case "NIZ_ZNAKOVA":
                checkCharArray(node);
                return;
            case "L_ZAGRADA":
                checkIzraz(scope, node);
                return;
        }
    }

    /**
     * <primarni_izraz> ::= IDN
     *
     * tip <- IDN.tip
     * l-izraz <- IDN.l-izraz
     *
     * 1. IDN.ime je deklarirano
     */
    private void checkIdentifier(Scope scope, SemanticNode node) {
        SemanticNode child = node.getChildAt(0);

        ScopeElement element = scope.getElement(child.getValue(), true);
        SemanticHelper.assertTrue(
                scope.isDeclared(child.getValue(), true),
                new SemanticException(node.errorOutput(), "Rule broken: 1. IDN.ime je deklarirano")
        );

        node.setType(element.getType());
        node.setLValue(element.getType() instanceof NumberType);
    }

    /**
     * <primarni_izraz> ::= BROJ
     *
     * tip <- int
     * l-izraz <- 0
     *
     * 1. vrijednost je u rasponu tipa int
     */
    private void checkInt(SemanticNode node) {
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        SemanticHelper.assertTrue(
                IntType.INSTANCE.validRange(node.getChildAt(0).getValue()),
                new SemanticException(node.errorOutput())
        );
    }

    /**
     * <primarni_izraz> ::= ZNAK
     *
     * tip <- char
     * l-izraz <- 0
     *
     * 1. znak je ispravan po 4.3.2
     */
    private void checkChar(SemanticNode node) {
        node.setType(CharType.INSTANCE);
        node.setLValue(false);

        SemanticHelper.assertTrue(
                CharType.INSTANCE.validRange(node.getChildAt(0).getValue()),
                new SemanticException(node.errorOutput())
        );
    }

    /**
     * <primarni_izraz> ::= NIZ_ZNAKOVA
     *
     * tip <- niz(const(char))
     * l-izraz <- 0
     *
     * 1. konstantni niz znakova je ispravan po 4.3.2
     */
    private void checkCharArray(SemanticNode node) {
        node.setType(new ArrayType(ConstType.CONST_CHAR));
        node.setLValue(false);

        SemanticHelper.assertTrue(
                ArrayType.validString(node.getChildAt(0).getValue()),
                new SemanticException(node.errorOutput())
        );
    }

    /**
     * <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
     *
     * tip <- <izraz>.tip
     * l-izraz <- <izraz>.l-izraz
     *
     * 1. provjeri(<izraz>)
     */
    private void checkIzraz(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(1);

        // 1.
        izraz.check(scope);

        node.setType(izraz.getType());
        node.setLValue(izraz.isLValue());
    }
}
