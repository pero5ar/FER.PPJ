package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rules;
import lab4.semantic.SemanticException;
import lab4.types.IntType;

public class UnarniIzraz extends BoilerplateIzraz {
    public UnarniIzraz() {
        super("<unarni_izraz>", Rules.POSTFIKS_IZRAZ.symbol);
    }

    // check 1 is boilerplate

    /**
     * Spaja produkciju 2 i 3. Koristeno ovako zbog boilerplate nasljedivanja.
     */
    @Override
    protected void check2(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.UNARNI_OPERATOR.symbol)) {
            check2_3(scope, node);
        } else {
            check2_2(scope, node);
        }
    }

    /**
     * <unarni_izraz> ::= (OP_INC | OP_DEC) <unarni_izraz>
     *
     * tip ← int
     * l-izraz ← 0
     *
     * 1. provjeri(<unarni_izraz>)
     * 2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip ∼ int
     */
    private void check2_2(Scope scope, SemanticNode node) {
        // tip ← int && l-izraz ← 0
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        SemanticNode unarniIzraz = node.getChildAt(1);
        // 1. provjeri(<unarni_izraz>)
        unarniIzraz.check(scope);

        // 2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip ∼ int
        if (!unarniIzraz.isLValue() || !unarniIzraz.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "Ruke broken: 2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip ∼ int");
        }
    }

    /**
     * <unarni_izraz> ::= <unarni_operator> <cast_izraz>
     *
     * tip ← int
     * l-izraz ← 0
     *
     * 1. provjeri(<cast_izraz>)
     * 2. <cast_izraz>.tip ∼ int
     */
    private void check2_3(Scope scope, SemanticNode node) {
        // tip ← int && l-izraz ← 0
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        SemanticNode castIzraz = node.getChildAt(1);
        // 1. provjeri(<cast_izraz>)
        castIzraz.check(scope);

        // 2. <cast_izraz>.tip ∼ int
        if (!castIzraz.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "Ruke broken: 2. <cast_izraz>.tip ∼ int");
        }
    }
}
