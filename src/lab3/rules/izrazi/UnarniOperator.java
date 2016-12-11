package lab3.rules.izrazi;

import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.rules.Rule;

public class UnarniOperator extends Rule {
    public UnarniOperator() {
        super("<unarni_operator>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        // u semantickoj analizi ovdje ne treba nista provjeriti
    }
}
