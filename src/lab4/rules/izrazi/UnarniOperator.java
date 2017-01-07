package lab4.rules.izrazi;

import lab4.models.Scope;
import lab4.models.SemanticNode;
import lab4.rules.Rule;

public class UnarniOperator extends Rule {
    public UnarniOperator() {
        super("<unarni_operator>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        // u semantickoj analizi ovdje ne treba nista provjeriti
    }
}
