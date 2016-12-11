package lab3.rules.izrazi;

import lab3.rules.Rules;

public class AditivniIzraz extends BoilerplateIzraz {
    public AditivniIzraz() {
        super("<aditivni_izraz>", Rules.MULTIPLIKATIVNI_IZRAZ.symbol);
    }
}
