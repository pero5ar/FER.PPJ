package lab4.lab3modified.rules.izrazi;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.IntType;

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
     * tip <- <y_izraz>.tip
     * l-izraz <- <y_izraz>.l-izraz
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
     * tip <- int
     * l-izraz <- 0
     *
     * 1. provjeri(<x_izraz>)
     * 2. <x_izraz>.tip ~ int
     * 3. provjeri(<y_izraz>)
     * 4. <y_izraz>.tip ~ int
     */
    protected void check2(Scope scope, SemanticNode node) {
        SemanticNode xIzraz = node.getChildAt(0);
        SemanticNode yIzraz = node.getChildAt(2);

        // tip <- int && l-izraz <- 0
        node.setType(IntType.INSTANCE);
        node.setLValue(false);

        // 1. provjeri(<x_izraz>)
        xIzraz.check(scope);

        // 2. <x_izraz>.tip ~ int
        SemanticHelper.assertTrue(
                xIzraz.getType().canImplicitCast(IntType.INSTANCE),
                new SemanticException(node.errorOutput(), "<x_izraz>.tip ~ int")
        );

        // 3. provjeri(<y_izraz>)
        yIzraz.check(scope);

        // 4. <y_izraz>.tip ~ int
        SemanticHelper.assertTrue(
                yIzraz.getType().canImplicitCast(IntType.INSTANCE),
                new SemanticException(node.errorOutput(), "<y_izraz>.tip ~ int")
        );
    }
}
