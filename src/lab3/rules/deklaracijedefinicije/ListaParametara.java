package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;
import lab3.types.Type;

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
     * tipovi <- [ <deklaracija_parametra>.tip ]
     * imena <- [ <deklaracija_parametra>.ime ]
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
     * tipovi <- <lista_parametara>.tipovi + [ <deklaracija_parametra>.tip ]
     * imena <- <lista_parametara>.imena + [ <deklaracija_parametra>.ime ]
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
        SemanticHelper.assertTrue(
                !listaParametara.getValues().contains(deklaracijaParametra.getValue()),
                new SemanticException(node.errorOutput(), "Rule broken: 3. <deklaracija_parametra>.ime ne postoji u <lista_parametara>.imena")
        );

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
