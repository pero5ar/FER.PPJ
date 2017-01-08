package lab4.lab3modified.rules.deklaracijedefinicije;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.rules.Rules;
import lab4.lab3modified.types.ArrayType;
import lab4.lab3modified.types.CharType;

import java.util.Collections;

/**
 * vidi: 4.4.6 (str. 71)
 */
public class Inicijalizator extends Rule {
    public Inicijalizator() {
        super("<inicijalizator>");
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
     * <inicijalizator> ::= <izraz_pridruzivanja>
     *
     * ako je <izraz_pridruzivanja> => NIZ_ZNAKOVA
     *      br-elem <- duljina niza znakova + 1
     *      tipovi <- lista duljine br-elem, svi elementi su char
     * inace
     *      tip <- <izraz_pridruzivanja>.tip
     *
     * 1. provjeri(<izraz_pridruzivanja>)
     */
    private void check1(Scope scope, SemanticNode node) {
        SemanticNode izrazPridruzivanja = node.getChildAt(0);

        // 1. provjeri(<izraz_pridruzivanja>)
        izrazPridruzivanja.check(scope);

        SemanticNode array = izrazPridruzivanja;
        do {
            array = array.getChildAt(0);
        } while (array.getChildren() != null && array.getChildren().size() == 1);

        if (array.getSymbol().equals("NIZ_ZNAKOVA")) {
            String text = array.getValue();

            int size = ArrayType.calcStringLength(text);
            node.setBrElem(size+1);
            node.setTypes(Collections.nCopies(size, CharType.INSTANCE));
        } else {
            node.setType(izrazPridruzivanja.getType());
        }
    }

    /**
     * <inicijalizator> ::= L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA
     *
     * br-elem <- <lista_izraza_pridruzivanja>.br-elem
     * tipovi <- <lista_izraza_pridruzivanja>.tipovi
     *
     * 1. provjeri(<lista_izraza_pridruzivanja>)
     */
    private void check2(Scope scope, SemanticNode node) {
        SemanticNode listaIzrazaPridruzivanja = node.getChildAt(1);

        // 1. provjeri(<lista_izraza_pridruzivanja>)
        listaIzrazaPridruzivanja.check(scope);

        // br-elem <- <lista_izraza_pridruzivanja>.br-elem &&
        // tipovi <- <lista_izraza_pridruzivanja>.tipovi
        node.setBrElem(listaIzrazaPridruzivanja.getBrElem());
        node.setTypes(listaIzrazaPridruzivanja.getTypes());
    }
}
