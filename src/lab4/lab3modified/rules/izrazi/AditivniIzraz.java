package lab4.lab3modified.rules.izrazi;

import lab4.frisc.CodeGenerator;
import lab4.frisc.InstructionGenerator;
import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.lab3modified.rules.Rules;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.types.IntType;

public class AditivniIzraz extends BoilerplateIzraz {
    public AditivniIzraz() {
        super("<aditivni_izraz>", Rules.MULTIPLIKATIVNI_IZRAZ.symbol);

    }

            @Override
            protected void check2(Scope scope, SemanticNode node) {
                SemanticNode xIzraz = node.getChildAt(0);
                SemanticNode yIzraz = node.getChildAt(2);
                String cNameFirst=null;
                String cNameSecond=null;

                // tip <- int && l-izraz <- 0
                node.setType(IntType.INSTANCE);
                node.setLValue(false);

                // 1. provjeri(<x_izraz>)
                xIzraz.check(scope);

                // 2. <x_izraz>.tip ~ int
                SemanticHelper.assertTrue(
                        xIzraz.getType().canImplicitCast(IntType.INSTANCE),
                        new SemanticException(node.errorOutput(), "<x_izraz>.tip ~ int")
                );
                if(node.getChildren().size()==3) {
                    cNameFirst = CodeGenerator.getNodeIDN();
                    CodeGenerator.setNodeIDN(null);
                    CodeGenerator.setIsNodeIDN(false);
                }

                // 3. provjeri(<y_izraz>)
                yIzraz.check(scope);

                // 4. <y_izraz>.tip ~ int
                SemanticHelper.assertTrue(
                        yIzraz.getType().canImplicitCast(IntType.INSTANCE),
                        new SemanticException(node.errorOutput(), "<y_izraz>.tip ~ int")
                );

                if(node.getChildren().size()==3) {
                    cNameSecond = CodeGenerator.getNodeIDN();
                    CodeGenerator.setNodeIDN(null);
                    CodeGenerator.setIsNodeIDN(false);
                }
                if(node.getChildren().size()==3) {
                    if(node.getChildAt(1).getSymbol().equals("PLUS")){
                        InstructionGenerator.addition(scope, cNameFirst, cNameSecond);
                        CodeGenerator.setIsNodeIzraz(true);
                    }
                    else if(node.getChildAt(1).getSymbol().equals("PLUS")){
                        //poziv 2;
                    }


                }
            }
    }

