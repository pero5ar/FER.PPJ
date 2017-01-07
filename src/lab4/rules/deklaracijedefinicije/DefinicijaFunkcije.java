package lab4.rules.deklaracijedefinicije;

import lab4.models.Scope;
import lab4.models.ScopeElement;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.semantic.SemanticException;
import lab4.semantic.SemanticHelper;
import lab4.types.ConstType;
import lab4.types.FunctionType;

import java.util.Iterator;

/**
 * vidi: 4.4.6 (str. 66)
 *
 */
public class DefinicijaFunkcije extends Rule {
    public DefinicijaFunkcije() {
        super("<definicija_funkcije>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(3, "KR_VOID")) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>
     *
     * 1. provjeri(<ime_tipa>)
     * 2. <ime_tipa>.tip != const(T)
     * 3. ne postoji prije definirana funkcija imena IDN.ime
     *
     * 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void → <ime_tipa>.tip)
     * 5. zabiljezi definiciju i deklaraciju funkcije
     * 6. provjeri(<slozena_naredba>)
     */
    private void check1(Scope scope, SemanticNode node) {
        subcheckRule1to3(scope, node);

        String functionName = node.getChildAt(1).getValue();
        FunctionType functionType = new FunctionType(node.getChildAt(0).getType(), null);

        // 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void → <ime_tipa>.tip)
        if (scope.isDeclared(functionName, true)
                && !scope.getElement(functionName, true).getType().equals(functionType)) {
            throw new SemanticException(node.errorOutput(),
                    "Definicija funkcije");
        }

        // 5. zabiljezi definiciju i deklaraciju funkcije
        node.setType(functionType);
        SemanticHelper.addDefinedFunction(functionName);
        scope.addElement(functionName, new ScopeElement(functionType, true));

        // 6. provjeri(<slozena_naredba>)
        Scope subScope = new Scope(scope);
        node.getChildAt(5).check(subScope);
    }

    /**
     * <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA <slozena_naredba>
     *
     * 1. provjeri(<ime_tipa>)
     * 2. <ime_tipa>.tip != const(T)
     * 3. ne postoji prije definirana funkcija imena IDN.ime
     *
     * 4. provjeri(<lista_parametara>)
     * 5. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(<lista_parametara>.tipovi → <ime_tipa>.tip)
     * 6. zabiljezi definiciju i deklaraciju funkcije
     * 7. provjeri(<slozena_naredba>) uz parametre funkcije koristeci <lista_parametara>.tipovi
     *  i <lista_parametara>.imena.
     */
    private void check2(Scope scope, SemanticNode node) {
        subcheckRule1to3(scope, node);

        SemanticNode listaParametara = node.getChildAt(3);
        listaParametara.check(scope);

        // 5. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(<lista_parametara>.tipovi → <ime_tipa>.tip)
        String functionName = node.getChildAt(1).getValue();
        FunctionType functionType = new FunctionType(node.getChildAt(0).getType(), listaParametara.getTypes());

        if (scope.isDeclared(functionName, true)
                && !scope.getElement(functionName, true).getType().equals(functionType)) {
            throw new SemanticException(node.errorOutput(),
                    "Definicija funkcije, produkcija 2 - krivi tip");
        }

        // 6. zabiljezi definiciju i deklaraciju funkcije
        node.setType(functionType);
        SemanticHelper.addDefinedFunction(functionName);
        scope.addElement(functionName, new ScopeElement(functionType, true));

        // 7. provjeri(<slozena_naredba>) uz parametre funkcije koristeci <lista_parametara>.tipovi i <lista_parametara>.imena.
        Scope subScope = new Scope(scope);
        subScope.addElement(functionName, new ScopeElement(functionType, true));
        loadSubscopeVariables(subScope, listaParametara);

        node.getChildAt(5).check(subScope);
    }

    private void loadSubscopeVariables(Scope subScope, SemanticNode listaParametara) {
        Iterator<String> namesIterator = listaParametara.getValues().iterator();
        listaParametara.getTypes().forEach(type -> {
            if (!namesIterator.hasNext()) {
                throw new IllegalStateException("Name count does not match count. Should not happen.");
            }
            subScope.addElement(namesIterator.next(), new ScopeElement(type, true));
        });
    }

    /**
     * 1. provjeri(<ime_tipa>)
     * 2. <ime_tipa>.tip != const(T)
     * 3. ne postoji prije definirana funkcija imena IDN.ime
     */
    private void subcheckRule1to3(Scope scope, SemanticNode node) {
        SemanticNode returnType = node.getChildAt(0);
        String functionName = node.getChildAt(1).getValue();

        // 1. provjeri(<ime_tipa>)
        returnType.check(scope);

        // 2. <ime_tipa>.tip != const(T)
        // 3. ne postoji prije definirana funkcija imena IDN.ime
        if (returnType.getType() instanceof ConstType
                || scope.isDefined(functionName, true)) {
            throw new SemanticException(node.errorOutput(),
                    "Definicija funkcije");
        }
    }
}
