package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rules;
import lab4.semantic.SemanticException;

public class CastIzraz extends BoilerplateIzraz {
    public CastIzraz() {
        super("<cast_izraz>", Rules.UNARNI_IZRAZ.symbol);
    }

    // check 1 is boilerplate

    /**
     * <cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
     *
     * tip ← <ime_tipa>.tip
     * l-izraz ← 0
     *
     * 1. provjeri(<ime_tipa>)
     * 2. provjeri(<cast_izraz>)
     * 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1
     */
    @Override
    protected void check2(Scope scope, SemanticNode node) {
        SemanticNode imeTipa = node.getChildAt(1);
        SemanticNode castIzraz = node.getChildAt(3);

        // tip ← <ime_tipa>.tip && l-izraz ← 0
        node.setType(imeTipa.getType());
        node.setLValue(false);

        // 1. provjeri(<ime_tipa>)
        imeTipa.check(scope);

        // 2. provjeri(<cast_izraz>)
        castIzraz.check(scope);

        // 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1
        if (!castIzraz.getType().canExplicitCast(imeTipa.getType())) {
            throw new SemanticException(node.errorOutput(),
                    "Rule broken: 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1");
        }
    }
}
