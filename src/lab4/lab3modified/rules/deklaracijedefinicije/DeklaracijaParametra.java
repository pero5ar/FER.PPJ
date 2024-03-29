package lab4.lab3modified.rules.deklaracijedefinicije;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.ArrayType;
import lab4.lab3modified.types.PrimitiveType;
import lab4.lab3modified.types.VoidType;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class DeklaracijaParametra extends Rule {
    public DeklaracijaParametra() {
        super("<deklaracija_parametra>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.getChildren().size() == 2) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <deklaracija_parametra> ::= <ime_tipa> IDN
     *
     * tip <- <ime_tipa>.tip
     * ime <- IDN.ime
     *
     * 1. provjeri(<ime_tipa>)
     * 2. <ime_tipa>.tip != void
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode imeTipa = node.getChildAt(0);

        // 1.
        imeTipa.check(scope);

        // 2.
        SemanticHelper.assertTrue(
                !VoidType.INSTANCE.equals(imeTipa.getType()),
                new SemanticException(node.errorOutput(), "Type can't be void")
        );

        node.setType(imeTipa.getType());
        node.setValue(node.getChildAt(1).getValue());
    }

    /**
     * <deklaracija_parametra> ::= <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA
     *
     * tip <- niz(<ime_tipa>.tip)
     * ime <- IDN.ime
     *
     * 1. provjeri(<ime_tipa>)
     * 2. <ime_tipa>.tip != void
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode imeTipa = node.getChildAt(0);

        // 1.
        imeTipa.check(scope);

        // 2.
        SemanticHelper.assertTrue(
                !VoidType.INSTANCE.equals(imeTipa.getType()),
                new SemanticException(node.errorOutput(), "Type can't be void")
        );

        try {
            PrimitiveType type = (PrimitiveType) imeTipa.getType();

            node.setType(new ArrayType(type));
            node.setValue(node.getChildAt(1).getValue());
        } catch(ClassCastException e) {
            throw new SemanticException(node.errorOutput(),
                    "Array type must be primitive."); // probably SHOULD NOT happen
        }
    }

}
