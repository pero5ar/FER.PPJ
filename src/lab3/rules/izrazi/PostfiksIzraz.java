package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.types.*;

import java.util.Iterator;
import java.util.List;

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

        if (node.childSymbolEqual(1, "L_ZAGRADA")
                && node.childSymbolEqual(2, "D_ZAGRADA")) {
            check3(scope, node);
            return;
        }

        if (node.childSymbolEqual(1, "L_ZAGRADA")
                && node.childSymbolEqual(2, Rules.LISTA_ARGUMENATA.symbol)
                && node.childSymbolEqual(3, "D_ZAGRADA")) {
            check4(scope, node);
            return;
        }

        check5(scope, node);
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
        Type typeX = arrayType.elementType;

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

    /**
     * <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA
     *
     * tip ← pov
     * l-izraz ← 0
     *
     * 1. provjeri(<postfiks_izraz>)
     * 2. <postfiks_izraz>.tip = funkcija(void → pov)
     */
    private void check3(Scope scope, SemanticNode node) {
        SemanticNode postfiksIzraz = node.getChildAt(0);
        postfiksIzraz.check(scope);

        FunctionType f;
        try{
            f = (FunctionType) postfiksIzraz.getType();
        } catch(ClassCastException e) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 2. <postfiks_izraz>.tip = funkcija(void → pov)");
        }

        if (f.parameters != null) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 2. <postfiks_izraz>.tip = funkcija(void → pov)");
        }

        node.setType(f.returnType);
        node.setLValue(false);
    }

    /**
     * <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
     *
     * tip ← pov
     * l-izraz ← 0
     *
     * 1. provjeri(<postfiks_izraz>)
     * 2. provjeri(<lista_argumenata>)
     * 3. <postfiks_izraz>.tip = funkcija(params → pov) i redom po elementima
     *      arg-tip iz <lista_argumenata>.tipovi i param-tip iz params vrijedi
     *      arg-tip ∼ param-tip
     */
    private void check4(Scope scope, SemanticNode node) {
        // 1.
        SemanticNode postfiksIzraz = node.getChildAt(0);
        postfiksIzraz.check(scope);

        // 2.
        SemanticNode listaArgumenata = node.getChildAt(2);
        listaArgumenata.check(scope);

        FunctionType f;
        try{
            f = (FunctionType) postfiksIzraz.getType();
        } catch(ClassCastException e) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 3. <postfiks_izraz>");
        }

        List<Type> arguments = listaArgumenata.getTypes();
        Iterator<Type> paramIterator = f.parameters.iterator();

        arguments.forEach(type -> {
            if (!paramIterator.hasNext()) {
                throw new SemanticException(node.errorOutput(),
                        "Param count does not match argument count");
            }
            if (!type.canImplicitCast(paramIterator.next())) {
                throw new SemanticException(node.errorOutput(),
                        "Invalid argument type");
            }
        });

        node.setType(f.returnType);
        node.setLValue(false);
    }


    /**
     * <postfiks_izraz> ::= <postfiks_izraz> (OP_INC | OP_DEC)
     *
     * tip ← int
     * l-izraz ← 0
     *
     * 1. provjeri(<postfiks_izraz>)
     * 2. <postfiks_izraz>.l-izraz = 1 i <postfiks_izraz>.tip ∼ int
     */
    private void check5(Scope scope, SemanticNode node) {
        SemanticNode postfiksIzraz = node.getChildAt(0);
        postfiksIzraz.check(scope);

        if (!postfiksIzraz.isLValue() || !IntType.INSTANCE.equals(postfiksIzraz.getType())) {
            throw new SemanticException(node.errorOutput());
        }

        // set type && l-izraz
        node.setType(IntType.INSTANCE);
        node.setLValue(false);
    }

}
