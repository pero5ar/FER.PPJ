package lab4.lab3modified.rules.naredbe;

import com.sun.org.apache.bcel.internal.classfile.Code;
import lab4.frisc.CodeGenerator;
import lab4.frisc.InstructionGenerator;
import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.rules.Rules;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.FunctionType;
import lab4.lab3modified.types.Type;
import lab4.lab3modified.types.VoidType;

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
                || node.childSymbolEqual(0, "KR_BREAK")) {
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
        // 1.
        SemanticNode currentNode = node;
        boolean inLoop = false;
        while(currentNode != null) {
            if (currentNode.getSymbol().equals(Rules.NAREDBA_PETLJE.symbol)) {
                inLoop = true;
                break;
            }

            currentNode = currentNode.getParent();
        }

        SemanticHelper.assertTrue(
                inLoop,
                new SemanticException(node.errorOutput(), "Must be inside of loop.")
        );
    }

    /**
     * <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
     *
     * 1. naredba se nalazi unutar funkcije tipa funkcija(params -> void)
     */
    private void check2(Scope scope, SemanticNode node) {
        FunctionType functionType = getFunctionType(node);
        SemanticHelper.assertTrue(
                functionType != null && VoidType.INSTANCE.equals(functionType.returnType),
                new SemanticException(node.errorOutput(), "Rule broken: naredba se nalazi unutar funkcije tipa funkcija(params -> void)")
        );


    }

    /**
     * <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ
     *
     * 1. provjeri(<izraz>)
     * 2. naredba se nalazi unutar funkcije tipa funkcija(params -> pov) i vrijedi
     * <izraz>.tip ~ pov
     */
    private void check3(Scope scope, SemanticNode node) {
        // 1. provjeri(<izraz>)
        SemanticNode izraz = node.getChildAt(1);
        izraz.check(scope);

        // 2. naredba se nalazi unutar funkcije tipa funkcija(params -> pov) i vrijedi
        // <izraz>.tip ~ pov
        FunctionType functionType = getFunctionType(node);
        SemanticHelper.assertTrue(
                functionType != null && izraz.getType().canImplicitCast(functionType.returnType),
                new SemanticException(node.errorOutput(), "Return type mismatch")
        );

        //genrator koda za return 42;
        if(CodeGenerator.isNodeBROJ()==true){
            InstructionGenerator.returnConst(scope, CodeGenerator.getNodeBROJ());
            CodeGenerator.setIsNodeBROJ(false);
            CodeGenerator.setNodeBROJ(0);

        }

        //generator koda za return x;
        else if (CodeGenerator.isNodeIDN()==true){
            InstructionGenerator.returnVar(scope, CodeGenerator.getNodeIDN());
            CodeGenerator.setIsNodeIDN(false);
            CodeGenerator.setNodeIDN(null);
        }
        else if(CodeGenerator.isNodeIzraz()==true){
            InstructionGenerator.returnVoid();
        }
    }

    private FunctionType getFunctionType(SemanticNode node) {
        if (node.getSymbol().equals(Rules.DEFINICIJA_FUNKCIJA.symbol)) {
            Type type = node.getType();
            SemanticHelper.assertTrue(
                    type instanceof FunctionType,
                    new IllegalStateException("Type should be FunctionType (actually " + type.getClass() + "). Something wrong happened.")
            );

            return (FunctionType) type;
        }

        SemanticNode parent = node.getParent();
        if (parent == null) {
            return null;
        }

        return getFunctionType(parent);
    }
}
