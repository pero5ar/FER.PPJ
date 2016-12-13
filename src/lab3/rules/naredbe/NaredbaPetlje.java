package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.semantic.SemanticException;
import lab3.types.IntType;

/**
 * @author pero
 */
public class NaredbaPetlje extends Rule {
    public NaredbaPetlje() {
        super("<naredba_petlje>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, "KR_WHILE")) {
            check1(scope, node);
            return;
        }
        if (node.getChildren().size() == 6) {
            check2(scope, node);
            return;
        }
        check3(scope, node);
    }

    /**
     * <naredba_petlje> ::= KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
     *
     * 1. provjeri(<izraz>)
     * 2. <izraz>.tip ∼ int
     * 3. provjeri(<naredba>)
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(2);
        SemanticNode naredba = node.getChildAt(4);

        //1
        izraz.check(scope);

        //2
        if (!izraz.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput());
        }

        //3
        naredba.check(new Scope(scope));

    }

    /**
     * <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba>1 <izraz_naredba>2
     * D_ZAGRADA <naredba>
     *
     * 1. provjeri(<izraz_naredba>1)
     * 2. provjeri(<izraz_naredba>2)
     * 3. <izraz_naredba>2.tip ∼ int
     * 4. provjeri(<naredba>)
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode naredba = node.getChildAt(5);

        //1, 2, 3
        checkFirstThree(scope, node);

        //4
        naredba.check(new Scope(scope));
    }

    /**
     * <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba>1 <izraz_naredba>2
     * <izraz> D_ZAGRADA <naredba>
     *
     * 1. provjeri(<izraz_naredba>1)
     * 2. provjeri(<izraz_naredba>2)
     * 3. <izraz_naredba>2.tip ∼ int
     * 4. provjeri(<izraz>)
     * 5. provjeri(<naredba>)
     */
    private void check3(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(4);
        SemanticNode naredba = node.getChildAt(6);

        //1, 2, 3
        checkFirstThree(scope, node);

        //4
        izraz.check(scope);

        //5
        naredba.check(new Scope(scope));
    }

    /**
     * 1. provjeri(<izraz_naredba>1)
     * 2. provjeri(<izraz_naredba>2)
     * 3. <izraz_naredba>2.tip ∼ int
     */
    private void checkFirstThree(Scope scope, SemanticNode node) {
        SemanticNode izrazNaredba1 = node.getChildAt(2);
        SemanticNode izrazNaredba2 = node.getChildAt(3);

        //1
        izrazNaredba1.check(scope);

        //2
        izrazNaredba2.check(scope);

        //3
        if (!izrazNaredba2.getType().canImplicitCast(IntType.INSTANCE)) {
            throw new SemanticException(node.errorOutput());
        }
    }
}
