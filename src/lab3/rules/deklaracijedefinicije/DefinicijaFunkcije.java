package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.semantic.SemanticException;
import lab3.types.ConstType;
import lab3.types.FunctionType;

import java.util.ArrayList;

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
        // TODO druga produkcija (check2)
        check1(scope, node);
    }

    /**
     * <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA
     * <slozena_naredba>
     *
     * 1. provjeri(<ime_tipa>)
     * 2. <ime_tipa>.tip != const(T)
     * 3. ne postoji prije definirana funkcija imena IDN.ime
     * 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void → <ime_tipa>.tip)
     * 5. zabiljezi definiciju i deklaraciju funkcije
     * 6. provjeri(<slozena_naredba>)
     */
    private void check1(Scope scope, SemanticNode node) {
        // 1. provjeri(<ime_tipa>)
        SemanticNode typeNode = node.getChildAt(0);
        typeNode.check(scope);

        // 2. <ime_tipa>.tip != const(T)
        if (typeNode.getType() instanceof ConstType) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: <ime_tipa>.tip != const(T)");
        }

        // 3. ne postoji prije definirana funkcija imena IDN.ime
        SemanticNode identifier = node.getChildAt(1);
        String functionName = identifier.getValue();
        if (scope.isDefined(identifier.getValue())) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: ne postoji prije definirana funkcija imena IDN.ime");
        }

        // 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void → <ime_tipa>.tip)
        FunctionType functionType = new FunctionType(typeNode.getType(), new ArrayList<>());
        if (scope.isDeclared(functionName) && !scope.getElement(functionName).getType().equals(functionType)) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void → <ime_tipa>.tip)");
        }

        // 5. zabiljezi definiciju i deklaraciju funkcije
        scope.addElement(functionName, new ScopeElement(functionType, true));
        node.setType(functionType);

        // 6. provjeri(<slozena_naredba>)
        Scope subScope = new Scope(scope);
        node.getChildAt(5).check(subScope);
    }
}
