package lab4.rules.deklaracijedefinicije;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;
import lab4.rules.Rules;
import lab4.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class ListaArgumenata extends Rule {
    public ListaArgumenata() {
        super("<lista_argumenata>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.IZRAZ_PRIDRUZIVANJA.symbol)) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <lista_argumenata> ::= <izraz_pridruzivanja>
     *
     * tipovi ← [ <izraz_pridruzivanja>.tip ]
     *
     * 1. provjeri(<izraz_pridruzivanja>)
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode izrazPridruzivanja = node.getChildAt(0);

        izrazPridruzivanja.check(scope);

        node.setType(izrazPridruzivanja.getType());
    }

    /**
     * <lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>
     *
     * tipovi ← <lista_argumenata>.tipovi + [ <izraz_pridruzivanja>.tip ]
     *
     * 1. provjeri(<lista_argumenata>)
     * 2. provjeri(<izraz_pridruzivanja>)
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode listaArgumenata = node.getChildAt(0);
        listaArgumenata.check(scope);

        SemanticNode izrazPridruzivanja = node.getChildAt(2);
        izrazPridruzivanja.check(scope);

        // set tipovi
        List<Type> types = new ArrayList<>(listaArgumenata.getTypes());
        types.add(izrazPridruzivanja.getType());
        node.setTypes(types);
    }

}
