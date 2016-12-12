package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;
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
        SemanticNode imeTipa = node.getChildAt(0);
        String functionName = node.getChildAt(1).getValue();
        FunctionType functionType = new FunctionType(imeTipa.getType(), new ArrayList<>());

        // 1. provjeri(<ime_tipa>)
        imeTipa.check(scope);

        // 2. <ime_tipa>.tip != const(T)
        // 3. ne postoji prije definirana funkcija imena IDN.ime
        if (imeTipa.getType() instanceof ConstType
                || scope.isDefined(functionName, true)) {
            throw new SemanticException(node.errorOutput(),
                    "Definicija funkcije");
        }

        // 4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni tip te deklaracije funkcija(void → <ime_tipa>.tip)
        if (scope.isDeclared(functionName)
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
}
