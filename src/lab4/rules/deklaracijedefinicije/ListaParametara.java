package lab4.rules.deklaracijedefinicije;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.rules.Rules;
import lab4.semantic.SemanticException;
import lab4.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class ListaParametara extends Rule {
    public ListaParametara() {
        super("<lista_parametara>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.DEKLARACIJA_PARAMETRA.symbol)) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <lista_parametara> ::= <deklaracija_parametra>
     *
     * tipovi ← [ <deklaracija_parametra>.tip ]
     * imena ← [ <deklaracija_parametra>.ime ]
     *
     * 1. provjeri(<deklaracija_parametra>)
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode deklaracijaParametra = node.getChildAt(0);

        deklaracijaParametra.check(scope);

        // lista se stvara automagicno u getteru
        node.setType(deklaracijaParametra.getType());
        node.setValue(deklaracijaParametra.getValue());
    }

    /**
     * <lista_parametara> ::= <lista_parametara> ZAREZ <deklaracija_parametra>
     *
     * tipovi ← <lista_parametara>.tipovi + [ <deklaracija_parametra>.tip ]
     * imena ← <lista_parametara>.imena + [ <deklaracija_parametra>.ime ]
     *
     * 1. provjeri(<lista_parametara>)
     * 2. provjeri(<deklaracija_parametra>)
     * 3. <deklaracija_parametra>.ime ne postoji u <lista_parametara>.imena
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode listaParametara = node.getChildAt(0);
        SemanticNode deklaracijaParametra = node.getChildAt(2);

        listaParametara.check(scope);
        deklaracijaParametra.check(scope);

        // 3.
        if (listaParametara.getValues().contains(deklaracijaParametra.getValue())) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 3. <deklaracija_parametra>.ime ne postoji u <lista_parametara>.imena");
        }

        // set tipovi
        List<Type> types = new ArrayList<>(listaParametara.getTypes());
        types.add(deklaracijaParametra.getType());

        node.setTypes(types);

        // set imena
        List<String> values = new ArrayList<>(listaParametara.getValues());
        values.add(deklaracijaParametra.getValue());

        node.setValues(values);
    }

}
