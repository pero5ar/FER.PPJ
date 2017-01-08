package lab4.lab3modified.rules.deklaracijedefinicije;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.ArrayType;
import lab4.lab3modified.types.ConstType;
import lab4.lab3modified.types.PrimitiveType;
import lab4.lab3modified.types.Type;

/**
 * vidi: 4.4.6 (str. 69)
 */
public class InitDeklarator extends Rule {
    public InitDeklarator() {
        super("<init_deklarator>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.getChildren().size() == 1) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <init_deklarator> ::= <izravni_deklarator>
     * 1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo
     *      <izravni_deklarator>.ntip <- <init_deklarator>.ntip
     * 2. <izravni_deklarator>.tip != const(T) i
     *    <izravni_deklarator>.tip != niz(const(T))
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode izravniDeklarator = node.getChildAt(0);

        // 1.
        izravniDeklarator.setNType(node.getNType());
        izravniDeklarator.check(scope);

        // 2.
        Type type = izravniDeklarator.getType();
        SemanticHelper.assertTrue(
                !(type instanceof ConstType) &&
                !(
                    type instanceof ArrayType &&
                    ((ArrayType) type).elementType instanceof ConstType
                ),
                new SemanticException(node.errorOutput(), "Rule broken: 2. <izravni_deklarator>.tip")
        );
    }

    /**
     * <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>
     *
     * 1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo
     *      <izravni_deklarator>.ntip <- <init_deklarator>.ntip
     * 2. provjeri(<incijalizator>)
     * 3. ako je <izravni_deklarator>.tip T ili const(T)
     *      <inicijalizator>.tip ~ T
     *    inace ako je <izravni_deklarator>.tip niz(T) ili niz(const(T))
     *      <inicijalizator>.br-elem <= <izravni_deklarator>.br-elem
     *      za svaki U iz <inicijalizator>.tipovi vrijedi U ~ T
     *    inace greska
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode izravniDeklarator = node.getChildAt(0);
        SemanticNode inicijalizator = node.getChildAt(2);

        // 1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo
        //      <izravni_deklarator>.ntip <- <init_deklarator>.ntip
        izravniDeklarator.setNType(node.getNType());
        izravniDeklarator.check(scope);

        // 2. provjeri(<incijalizator>)
        inicijalizator.check(scope);

        // 3. ako je <izravni_deklarator>.tip T ili const(T)
        //      (a) <inicijalizator>.tip ~ T
        //    inace ako je <izravni_deklarator>.tip niz(T) ili niz(const(T))
        //      <inicijalizator>.br-elem <= <izravni_deklarator>.br-elem
        //      za svaki U iz <inicijalizator>.tipovi vrijedi U ~ T
        //    inace greska
        Type type = izravniDeklarator.getType();
        Type targetType;
        if (type instanceof PrimitiveType) {
            targetType = (type instanceof ConstType) ? ((ConstType) type).wrappedType
                    : type;

            // (a)
            SemanticHelper.assertTrue(
                    inicijalizator.getType() != null && inicijalizator.getType().canImplicitCast(targetType),
                    new SemanticException(node.errorOutput(), "Rule broken: 3. (init_deklarator produkcija 2)")
            );
        } else if (type instanceof ArrayType) {
            type = ((ArrayType) type).elementType;
            targetType = (type instanceof ConstType) ? ((ConstType) type).wrappedType
                    : type;

            SemanticHelper.assertTrue(
                    inicijalizator.getBrElem() >= 0 && inicijalizator.getBrElem() >= 0 &&
                            inicijalizator.getBrElem() <= izravniDeklarator.getBrElem(),
                    new SemanticException(node.errorOutput(), "Rule broken: 3. (init_deklarator produkcija 2)")
            );

            inicijalizator.getTypes().forEach(sourceType -> SemanticHelper.assertTrue(
                    sourceType.canImplicitCast(targetType),
                    new SemanticException(node.errorOutput(), "Rule broken: 3. (init_deklarator produkcija 2)")
            ));
        } else {
            throw new SemanticException(node.errorOutput(), "Invalid type");
        }
    }
}
