package lab3.rules.naredbe;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.FunctionType;
import lab3.types.Type;
import lab3.types.VoidType;

/**
 * vidi: 4.4.5 (str. 62)
 *
 * @author JJ
 */
public class NaredbaSkoka extends Rule {
    public NaredbaSkoka() {
        super("<naredba_skoka>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, "KR_CONTINUE")
                || node.childSymbolEqual(1, "KR_BREAK")) {
            check1(scope, node);
        } else if (node.childSymbolEqual(0, "KR_RETURN")) {
            if (node.childSymbolEqual(1, "TOCKAZAREZ")) {
                check2(scope, node);
            } else {
                // izraz
                check3(scope, node);
            }
        }
    }

    /**
     * <naredba_skoka> ::= (KR_CONTINUE | KR_BREAK) TOCKAZAREZ
     *
     * 1. naredba se nalazi unutar petlje ili unutar bloka koji je ugnijezden u petlji
     */
    private void check1(Scope scope, SemanticNode node) {
        // TODO
//        if (!checkLoop(node)) {
//            throw new SemanticException(node.errorOutput(), "Must be inside of loop to use break / continue.");
//        }
    }

    /**
     * <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
     *
     * 1. naredba se nalazi unutar funkcije tipa funkcija(params → void)
     */
    private void check2(Scope scope, SemanticNode node) {
        FunctionType functionType = getFunctionType(node);
        if (functionType == null || !functionType.returnType.equals(VoidType.INSTANCE)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: naredba se nalazi unutar funkcije tipa funkcija(params → void)");
        }
    }

    /**
     * <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ
     *
     * 1. provjeri(<izraz>)
     * 2. naredba se nalazi unutar funkcije tipa funkcija(params → pov) i vrijedi
     * <izraz>.tip ∼ pov
     */
    private void check3(Scope scope, SemanticNode node) {
        // 1. provjeri(<izraz>)
        SemanticNode izraz = node.getChildAt(1);
        izraz.check(scope);

        // 2. naredba se nalazi unutar funkcije tipa funkcija(params → pov) i vrijedi
        // <izraz>.tip ∼ pov
        FunctionType functionType = getFunctionType(node);
        if (functionType == null || !izraz.getType().canImplicitCast(functionType.returnType)) {
            throw new SemanticException(node.errorOutput(),
                    "Return type mismatch");
        }
    }

    // TODO
//    private boolean checkLoop(SemanticNode node) {
//        return node.getSymbol().equals(Rules.NAREDBA_PETLJE.symbol)
//                || node.getParent() != null && checkLoop(node.getParent());
//    }

    private FunctionType getFunctionType(SemanticNode node) {
        if (node.getSymbol().equals(Rules.DEFINICIJA_FUNKCIJA.symbol)) {
            Type type = node.getType();
            if (!(type instanceof FunctionType)) {
                throw new IllegalStateException("Type should be FunctionType (actually " + type.getClass() + "). Something wrong happened.");
            }

            return (FunctionType) type;
        }

        SemanticNode parent = node.getParent();
        if (parent == null) {
            return null;
        }

        return getFunctionType(parent);
    }
}
