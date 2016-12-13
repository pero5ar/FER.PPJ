package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.types.IntType;

/**
 * vidi: 4.4.5 (str. 62)
 *
 * @author JJ
 */
public class NaredbaGrananja extends Rule {
    public NaredbaGrananja() {
        super("<naredba_grananja>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if(node.getChildren().size()==5){
            check1(scope, node);
        } else{
            check2(scope, node);
        }
    }

    /**
     * <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
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
     * <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>1
     * KR_ELSE <naredba>2
     *
     * 1. provjeri(<izraz>)
     * 2. <izraz>.tip ∼ int
     * 3. provjeri(<naredba>1)
     * 4. provjeri(<naredba>2)
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode izraz = node.getChildAt(2);
        SemanticNode naredba1 = node.getChildAt(4);
        SemanticNode naredba2 = node.getChildAt(6);

        //1
        izraz.check(scope);
        //2
        izraz.getType().canImplicitCast(IntType.INSTANCE);
        //3
        naredba1.check(new Scope(scope));
        //4
        naredba2.check(new Scope(scope));
    }
}
