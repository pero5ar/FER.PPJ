package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.semantic.SemanticException;
import lab4.types.IntType;

import java.util.function.Predicate;

/**
 * Since <bin_
 */
public abstract class BoilerplateIzraz extends Rule {
    protected Predicate<SemanticNode> production1Predicate;

    protected BoilerplateIzraz(String symbol, String production1Symbol) {
        this(
                symbol,
                (node -> node.childSymbolEqual(0, production1Symbol))
        );
    }

    protected BoilerplateIzraz(String symbol, Predicate<SemanticNode> production1Predicate) {
        super(symbol);
        this.production1Predicate = production1Predicate;
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (production1Predicate.test(node)) {
            check1(scope, node);
        } else {
            check2(scope, node);
        }
    }

    /**
     * <boilerplate_izraz> ::= <y_izraz>
     *
     * tip ← <y_izraz>.tip
     * l-izraz ← <y_izraz>.l-izraz
     *
     * 1. provjeri(<y_izraz>)
     */
    protected void check1(Scope scope, SemanticNode node) {
        SemanticNode yIzraz = node.getChildAt(0);

        yIzraz.check(scope);

        node.setType(yIzraz.getType());
        node.setLValue(yIzraz.isLValue());
    }

    /**
     * Composite verification used in many expressions.
     ***********
     * Defined as:
     *
     * <boilerplate_izraz> ::= <x_izraz> (OP) <y_izraz>
     *
     * tip ← int
     * l-izraz ← 0
     *
     * 1. provjeri(<x_izraz>)
     * 2. <x_izraz>.tip ∼ int
     * 3. provjeri(<y_izraz>)
     * 4. <y_izraz>.tip ∼ int
     */
    protected void check2(Scope scope, SemanticNode node) {
        SemanticNode xIzraz = node.getChildAt(0);
        SemanticNode yIzraz = node.getChildAt(2);

        // tip ← int && l-izraz ← 0
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        // 1. provjeri(<x_izraz>)
        xIzraz.check(scope);

        // 2. <x_izraz>.tip ∼ int
        if (!xIzraz.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "<x_izraz>.tip ∼ int");
        }

        // 3. provjeri(<y_izraz>)
        yIzraz.check(scope);

        // 4. <y_izraz>.tip ∼ int
        if (!yIzraz.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 4. <y_izraz>.tip ∼ int");
        }
    }
}
