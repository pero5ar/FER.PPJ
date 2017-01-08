package lab4.lab3modified.rules.deklaracijedefinicije;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.rules.Rules;
import lab4.lab3modified.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class ListaIzrazaPridruzivanja extends Rule {
    public ListaIzrazaPridruzivanja() {
        super("<lista_izraza_pridruzivanja>");
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
     * <lista_izraza_pridruzivanja> ::= <izraz_pridruzivanja>
     *
     * tipovi <- [ <izraz_pridruzivanja>.tip ]
     * br-elem <- 1
     *
     * 1. provjeri (<izraz_pridruzivanja>)
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode izrazPridruzivanja = node.getChildAt(0);

        // 1. provjeri (<izraz_pridruzivanja>)
        izrazPridruzivanja.check(scope);

        node.setType(izrazPridruzivanja.getType());
        node.setBrElem(1);
    }

    /**
     * <lista_izraza_pridruzivanja> ::= <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>
     *
     * tipovi <- <lista_izraza_pridruzivanja>.tipovi + [ <izraz_pridruzivanja>.tip ]
     * br-elem <- <lista_izraza_pridruzivanja>.br-elem+ 1
     *
     * 1. provjeri (<lista_izraza_pridruzivanja>)
     * 2. provjeri (<izraz_pridruzivanja>)
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode listaIzrazaPridruzivanja = node.getChildAt(0);
        SemanticNode izrazPridruzivanja = node.getChildAt(2);

        // 1.
        listaIzrazaPridruzivanja.check(scope);

        // 2.
        izrazPridruzivanja.check(scope);

        // set tipovi
        List<Type> types = new ArrayList<>(listaIzrazaPridruzivanja.getTypes());
        types.add(izrazPridruzivanja.getType());
        node.setTypes(types);

        // set br. elem
        node.setBrElem(listaIzrazaPridruzivanja.getBrElem()+1);
    }
}
