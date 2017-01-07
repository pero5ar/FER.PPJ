package lab4.rules.naredbe;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.types.IntType;

/**
 * vidi: 4.4.5 (str. 62)
 *
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
        } else if (node.getChildren().size() == 5) {
            check2(scope, node);
        } else {
            check3(scope, node);
        }
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
        izraz.getType().canImplicitCast(IntType.INSTANCE);

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
        SemanticNode izrazNaredba1 = node.getChildAt(2);
        SemanticNode izrazNaredba2 = node.getChildAt(3);
        SemanticNode naredba = node.getChildAt(5);

        //1, 2, 3
        checkFirstThree(scope, izrazNaredba1, izrazNaredba2);

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
        SemanticNode izrazNaredba1 = node.getChildAt(2);
        SemanticNode izrazNaredba2 = node.getChildAt(3);
        SemanticNode izraz = node.getChildAt(4);
        SemanticNode naredba = node.getChildAt(6);

        //1, 2, 3
        checkFirstThree(scope, izrazNaredba1, izrazNaredba2);

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
    private void checkFirstThree(Scope scope, SemanticNode izrazNaredba1, SemanticNode izrazNaredba2) {
        //1
        izrazNaredba1.check(scope);

        //2
        izrazNaredba2.check(scope);

        //3
        izrazNaredba2.getType().canImplicitCast(IntType.INSTANCE);
    }
}
