package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;
import lab3.types.*;

/**
 * vidi: 4.4.6 (str. 69)
 */
public class IzravniDeklarator extends Rule {
    public IzravniDeklarator() {
        super("<izravni_deklarator>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.getChildren().size() == 1) {
            check1(scope, node);
            return;
        }
        if (node.childSymbolEqual(2, "BROJ")) {
            check2(scope, node);
            return;
        }
        if (node.childSymbolEqual(2, "KR_VOID")) {
            check3(scope, node);
            return;
        }
        if (node.childSymbolEqual(2, Rules.LISTA_PARAMETARA.symbol)) {
            check4(scope, node);
            return;
        }
    }

    /**
     * <izravni_deklarator> ::= IDN
     *
     * tip ← ntip
     *
     * 1. ntip != void
     * 2. IDN.ime nije deklarirano u lokalnom djelokrugu
     * 3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
     */
    private void check1(Scope scope, SemanticNode node) {
        String name = node.getChildAt(0).getValue();

        if (scope.isDeclared(name) || node.getNType().equals(VoidType.INSTANCE)) {
            throw new SemanticException(node.errorOutput());
        }

        scope.addElement(
                name,
                new ScopeElement(node.getNType(), true)
        );

        node.setType(node.getNType());
    }

    /**
     * <izravni_deklarator> ::= IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
     *
     * tip ← niz(ntip)
     * br-elem ← BROJ.vrijednost
     *
     * 1. ntip != void
     * 2. IDN.ime nije deklarirano u lokalnom djelokrugu
     * 3. BROJ.vrijednost je pozitivan broj (> 0) ne veci od 1024
     * 4. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
     */
    private void check2(Scope scope, SemanticNode node) {
        String name = node.getChildAt(0).getValue();

        Type nTip = node.getNType();
        if (VoidType.INSTANCE.equals(nTip)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: ntip != void");
        }

        if (scope.isDeclared(name, false)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: IDN.ime nije deklarirano u lokalnom djelokrugu");
        }

        SemanticNode broj = node.getChildAt(2);
        int brojVrijednost;
        try {
            brojVrijednost = Integer.parseInt(broj.getValue());
        } catch (NumberFormatException e) {
            throw new SemanticException(node.errorOutput(),
                    "Int error");
        }
        if (brojVrijednost <= 0 || brojVrijednost > 1024) {
            throw new SemanticException(node.errorOutput(),
                    "Index error");
        }

        if (!(nTip instanceof PrimitiveType)) {
            // probably should not occur
            throw new SemanticException(node.errorOutput(),
                    "type must be primitive.");
        }

        Type type = new ArrayType((PrimitiveType)nTip);
        node.setType(type);
        scope.addElement(name, new ScopeElement(
                type,
                false
        ));
        node.setBrElem(brojVrijednost);
    }



    /**
     * <izravni_deklarator> ::= IDN L_ZAGRADA KR_VOID D_ZAGRADA
     *
     * tip ← funkcija(void → ntip)
     *
     * 1. ako je IDN.ime deklarirano u lokalnom djelokrugu, tip prethodne deklaracije
     *  je jednak funkcija(void → ntip)
     * 2. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom ako ista funkcija vec nije
     *  deklarirana u lokalnom djelokrugu
     */
    private void check3(Scope scope, SemanticNode node) {
        String name = node.getChildAt(0).getValue();

        Type nTip = node.getNType();
        ScopeElement previousDeclaration = scope.getElement(name, false);

        Type type = new FunctionType(nTip, null);
        if (previousDeclaration != null &&
                !previousDeclaration.getType().equals(type)) {
            // ako debugiras ovo, moguce da se FunctionType void ne sprema kao null. (pogledaj u varijablu type)
            throw new SemanticException(node.errorOutput(),
                    "Izravni deklarator.");
        } else {
            scope.addElement(name, new ScopeElement(type, false));
            SemanticHelper.addDeclaredFunction(name, type);
        }

        node.setType(type);
    }

    /**
     * <izravni_deklarator> ::= IDN L_ZAGRADA <lista_parametara> D_ZAGRADA
     *
     * tip ← funkcija(<lista_parametara>.tipovi → ntip)
     *
     * 1. provjeri(<lista_parametara>)
     * 2. ako je IDN.ime deklarirano u lokalnom djelokrugu, tip prethodne deklaracije
     *  je jednak funkcija(<lista_parametara>.tipovi → ntip)
     * 3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom ako ista funkcija vec nije
     *  deklarirana u lokalnom djelokrugu
     */
    private void check4(Scope scope, SemanticNode node) {
        SemanticNode listaParametara = node.getChildAt(2);

        listaParametara.check(scope);

        // 2.
        String name = node.getChildAt(0).getValue();
        Type nTip = node.getNType();

        ScopeElement previousDeclaration = scope.getElement(name, false);

        Type type = new FunctionType(nTip, listaParametara.getTypes());
        if (previousDeclaration != null &&
                !previousDeclaration.getType().equals(type)) {
            throw new SemanticException(node.errorOutput(),
                    "Izravni deklarator.");
        } else {
            scope.addElement(name, new ScopeElement(type, false));
            SemanticHelper.addDeclaredFunction(name, type);
        }

        node.setType(type);
    }
}
