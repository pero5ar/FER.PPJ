package lab4.lab3modified.rules.izrazi;

import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;

public class UnarniOperator extends Rule {
    public UnarniOperator() {
        super("<unarni_operator>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        // u semantickoj analizi ovdje ne treba nista provjeriti
    }
}
