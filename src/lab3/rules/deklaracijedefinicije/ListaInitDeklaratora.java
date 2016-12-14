package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;
import lab3.rules.Rules;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class ListaInitDeklaratora extends Rule {
    public ListaInitDeklaratora() {
        super("<lista_init_deklaratora>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.INIT_DEKLARATOR.symbol)) {
            check1(scope, node);
            return;
        }
        check2(scope, node);
    }

    /**
     * <lista_init_deklaratora> ::= <init_deklarator>
     *
     * 1. provjeri(<init_deklarator>) uz nasljedno svojstvo
     *      <init_deklarator>.ntip <- <lista_init_deklaratora>.ntip
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode initDeklarator = node.getChildAt(0);

        // 1. provjeri(<init_deklarator>) uz nasljedno svojstvo
        //      <init_deklarator>.ntip <- <lista_init_deklaratora>.ntip
        initDeklarator.setNType(node.getNType());
        initDeklarator.check(scope);
    }

    /**
     * <lista_init_deklaratora>1 ::= <lista_init_deklaratora>2 ZAREZ <init_deklarator>
     *
     * 1. provjeri(<lista_init_deklaratora>2) uz nasljedno svojstvo
     *      <lista_init_deklaratora>2.ntip <- <lista_init_deklaratora>1.ntip
     * 2. provjeri(<init_deklarator>) uz nasljedno svojstvo
     *      <init_deklarator>.ntip <- <lista_init_deklaratora>1.ntip
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode listaInitDeklaratora = node.getChildAt(0);
        SemanticNode initDeklarator = node.getChildAt(2);

        listaInitDeklaratora.setNType(node.getNType());
        initDeklarator.setNType(node.getNType());

        // 1. provjeri(<lista_init_deklaratora>2)
        listaInitDeklaratora.check(scope);

        // 2. provjeri(<init_deklarator>)
        initDeklarator.check(scope);
    }
}
