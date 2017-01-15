package lab4.lab3modified.rules.naredbe;

import lab4.frisc.CodeGenerator;
import lab4.frisc.InstructionGenerator;
import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rule;
import lab4.lab3modified.rules.Rules;

/**
 * vidi: 4.4.5 (str. 62)
 *
 * @author JJ
 */
public class Naredba extends Rule {
    public Naredba() {
        super("<naredba>");
    }

    @Override
    public void check(Scope scope, SemanticNode node) {
        if (node.childSymbolEqual(0, Rules.SLOZENA_NAREDBA.symbol)) {
            // <slozena_naredba> - stvori novi djelokrug
            node.getChildAt(0).check(new Scope(scope));
        } else {
            // <izraz_naredba>, <naredba_grananja>, <naredba_petlje> i <naredba_skoka>
            node.getChildAt(0).check(scope);
        }

        if(node.getChildren().size()>1 && (CodeGenerator.getIsInkrementPrije() || CodeGenerator.getIsInkrementIzrazPoslije()) && !CodeGenerator.isIsNodeIDNSecond()){
            InstructionGenerator.inkrement(scope, CodeGenerator.getNodeIDN());
            CodeGenerator.setNodeIDN(null);
            CodeGenerator.setIsNodeIDN(false);
            CodeGenerator.setIsInkrementIzrazPoslije(false);
            CodeGenerator.setIsInkrementPrije(false);

        }
        if(node.getChildren().size()>1 && (CodeGenerator.getIsInkrementPrije() || CodeGenerator.getIsInkrementIzrazPoslije()) && CodeGenerator.isIsNodeIDNSecond()){
            if(CodeGenerator.getIsInkrementPrije()) {
                InstructionGenerator.pridruziVrijednostVarijeble(scope, CodeGenerator.getNodeIDN(),CodeGenerator.getNodeIDNSecond());
                InstructionGenerator.inkrement(scope, CodeGenerator.getNodeIDN());
            } else{
                InstructionGenerator.inkrement(scope, CodeGenerator.getNodeIDN());
            }
            CodeGenerator.setNodeIDN(null);
            CodeGenerator.setIsNodeIDN(false);
            CodeGenerator.setIsInkrementIzrazPoslije(false);
            CodeGenerator.setIsInkrementPrije(false);

        }
    }
}
