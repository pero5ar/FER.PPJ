package lab3.rules.deklaracijedefinicije;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;

/**
 * vidi: 4.4.6 (str. 68)
 */
public class Deklaracija extends Rule {
    public Deklaracija() {
        super("<deklaracija>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        // <deklaracija> ::= <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ

        // 1. provjeri(<ime_tipa>)
        SemanticNode imeTipa = node.getChildAt(0);
        imeTipa.check(scope);

        // 2. provjeri(<lista_init_deklaratora>) uz nasljedno svojstvo
        //      <lista_init_deklaratora>.ntip ← <ime_tipa>.tip
        SemanticNode listaInitDeklaratora = node.getChildAt(1);
        listaInitDeklaratora.setNType(imeTipa.getType());
        listaInitDeklaratora.check(scope);
    }

}
