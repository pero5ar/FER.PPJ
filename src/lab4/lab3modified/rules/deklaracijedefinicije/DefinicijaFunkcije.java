package lab4.lab3modified.rules.deklaracijedefinicije;

import lab4.frisc.CodeGenerator;
import lab4.frisc.InstructionGenerator;
import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.ScopeElement;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.ConstType;
import lab4.lab3modified.types.FunctionType;

import java.util.ArrayList;
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
     * 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void -> <ime_tipa>.tip)
     * 5. zabiljezi definiciju i deklaraciju funkcije
     * 6. provjeri(<slozena_naredba>)
     */
    private void check1(Scope scope, SemanticNode node) {

        //genrator koda za int main(void){
        if(node.getChildAt(1).getValue().equals("main")){
            InstructionGenerator.definicijaIntFunkcije("main", new ArrayList<String>(), scope);
        }
        else if(node.getChildren().size()==6){
            if(node.getChildAt(3).getSymbol().equals("KR_VOID")){
                InstructionGenerator.definicijaIntFunkcije(node.getChildAt(1).getValue(), new ArrayList<String>(), scope);

            }
        }

        subcheckRule1to3(scope, node);

        String functionName = node.getChildAt(1).getValue();
        FunctionType functionType = new FunctionType(node.getChildAt(0).getType(), null);

        // 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void -> <ime_tipa>.tip)
        SemanticHelper.assertTrue(
                !scope.isDeclared(functionName, false) || scope.getElement(functionName, true).getType().equals(functionType),
                new SemanticException(node.errorOutput(), "Definicija funkcije")
        );

        // 5. zabiljezi definiciju i deklaraciju funkcije
        node.setType(functionType);
        SemanticHelper.addDefinedFunction(functionName, functionType);
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
     * 5. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(<lista_parametara>.tipovi -> <ime_tipa>.tip)
     * 6. zabiljezi definiciju i deklaraciju funkcije
     * 7. provjeri(<slozena_naredba>) uz parametre funkcije koristeci <lista_parametara>.tipovi
     *  i <lista_parametara>.imena.
     */
    private void check2(Scope scope, SemanticNode node) {
        subcheckRule1to3(scope, node);

        SemanticNode listaParametara = node.getChildAt(3);
        listaParametara.check(scope);

        // 5. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(<lista_parametara>.tipovi -> <ime_tipa>.tip)
        String functionName = node.getChildAt(1).getValue();
        FunctionType functionType = new FunctionType(node.getChildAt(0).getType(), listaParametara.getTypes());

        SemanticHelper.assertTrue(
                !scope.isDeclared(functionName, true) || scope.getElement(functionName, true).getType().equals(functionType),
                new SemanticException(node.errorOutput(), "Definicija funkcije, produkcija 2 - krivi tip")
        );

        // 6. zabiljezi definiciju i deklaraciju funkcije
        node.setType(functionType);
        SemanticHelper.addDefinedFunction(functionName, functionType);
        scope.addElement(functionName, new ScopeElement(functionType, true));

        // 7. provjeri(<slozena_naredba>) uz parametre funkcije koristeci <lista_parametara>.tipovi i <lista_parametara>.imena.
        Scope subScope = new Scope(scope);
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
        SemanticHelper.assertTrue(
                !(returnType.getType() instanceof ConstType) && !scope.isDefined(functionName, true),
                new SemanticException(node.errorOutput(), "Definicija funkcije")
        );
    }
}
