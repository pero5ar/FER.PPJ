package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.ArrayType;
import lab3.types.ConstType;
import lab3.types.IntType;
import lab3.types.Type;

public class PostfiksIzraz extends Rule {
    public PostfiksIzraz() {
        super("<postfiks_izraz>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.PRIMARNI_IZRAZ.symbol)) {
            check1(scope, node);
            return;
        }

        if (node.childSymbolEqual(1, "L_UGL_ZAGRADA")) {
            check2(scope, node);
            return;
        }

        // TODO Ostale produkcije
    }

    /**
     * <postfiks_izraz> ::= <primarni_izraz>
     *
     * tip ← <primarni_izraz>.tip
     * l-izraz ← <primarni_izraz>.l-izraz
     *
     * 1. provjeri(<primarni_izraz>)
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode primarniIzraz = node.getChildAt(0);

        // 1. provjeri(<primarni_izraz>)
        primarniIzraz.check(scope);

        // tip ← <primarni_izraz>.tip && l-izraz ← <primarni_izraz>.l-izraz
        node.setType(primarniIzraz.getType());
        node.setLValue(primarniIzraz.isLValue());
    }

    /**
     * <postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
     *
     * tip ← X
     * l-izraz ← X 6= const(T)
     *
     * 1. provjeri(<postfiks_izraz>)
     * 2. <postfiks_izraz>.tip = niz(X)
     * 3. provjeri(<izraz>)
     * 4. <izraz>.tip ∼ int
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode postfiksIzraz = node.getChildAt(0);
        SemanticNode izraz = node.getChildAt(2);

        // 1. provjeri(<postfiks_izraz>)
        postfiksIzraz.check(scope);

        // 2. <postfiks_izraz>.tip = niz(X)
        if (!(postfiksIzraz.getType() instanceof ArrayType)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 2. <postfiks_izraz>.tip = niz(X)");
        }

        // tip ← X && l-izraz ← X != const(T)
        ArrayType arrayType = (ArrayType) postfiksIzraz.getType();
        Type typeX = arrayType.getElementType();

        node.setType(typeX);
        node.setLValue(!(typeX instanceof ConstType));

        // 3. provjeri(<izraz>)
        izraz.check(scope);

        // 4. <izraz>.tip ∼ int
        if (!izraz.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 4. <izraz>.tip ∼ int");
        }

    }

}
