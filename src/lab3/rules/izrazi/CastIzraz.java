package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rules;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;

public class CastIzraz extends BoilerplateIzraz {
    public CastIzraz() {
        super("<cast_izraz>", Rules.UNARNI_IZRAZ.symbol);
    }

    public void check(Scope scope, SemanticNode node) {
        super.check(scope, node);
    }

    // check 1 is boilerplate

    /**
     * <cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
     *
     * tip <- <ime_tipa>.tip
     * l-izraz <- 0
     *
     * 1. provjeri(<ime_tipa>)
     * 2. provjeri(<cast_izraz>)
     * 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1
     */
    @Override
    protected void check2(Scope scope, SemanticNode node) {
        SemanticNode imeTipa = node.getChildAt(1);
        SemanticNode castIzraz = node.getChildAt(3);

        // 1. provjeri(<ime_tipa>)
        imeTipa.check(scope);

        // tip <- <ime_tipa>.tip && l-izraz <- 0
        node.setType(imeTipa.getType());
        node.setLValue(false);

        // 2. provjeri(<cast_izraz>)
        castIzraz.check(scope);

        // 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1
        SemanticHelper.assertTrue(
                castIzraz.getType().canExplicitCast(imeTipa.getType()),
                new SemanticException(node.errorOutput(), "Rule broken: 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1")
        );
    }
}
