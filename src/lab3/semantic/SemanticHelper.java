package lab3.semantic;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.types.FunctionType;
import lab3.types.IntType;

import java.util.ArrayList;
import java.util.List;

public class SemanticHelper {
    private static List<String> declaredFunctions = new ArrayList<>();
    private static List<String> definedFunctions = new ArrayList<>();

    /**
     * Vidi: 4.4.7 (str. 72)
     */
    public static void postTreeWalk() {
        if (!validateMain()) {
            throw new SemanticException("main");
        }

        checkFunctionDeclarations(Scope.globalScope);
    }

    public static void addDeclaredFunction(String name) {
        declaredFunctions.add(name);
    }

    public static void addDefinedFunction(String name) {
        definedFunctions.add(name);
    }

    private static boolean validateMain() {
        if (!Scope.globalScope.isDeclared("main")) {
            return false;
        }

        ScopeElement main = Scope.globalScope.getElement("main", false);
        if (!(main.defined && main.getType() instanceof FunctionType)) {
            return false;
        }

        FunctionType mainType = (FunctionType) main.getType();
        return !(!(mainType.returnType.equals(IntType.INSTANCE)) || mainType.parameters.isEmpty());

//        throw new SemanticException("main");
    }

    private static void checkFunctionDeclarations(Scope scope) {
//        scope.getElementTable().forEach((name, element) -> {
//            if (!(element.getType() instanceof FunctionType)){
//                // element nije funkcija; idi dalje
//                return;
//            }
//
//            ScopeElement declaration = Scope.globalScope.getElement(name, false);
//
//            // globalna funkcija (odnosno deklaracija funkcije) mora postojati, /*biti definirana*/
//            // i istog tipa kao i definicija funkcije
//            if ( declaration == null
//                    /*|| !globalFunction.defined*/
//                    || !declaration.getType().equals(element.getType())
//                ) {
//                throw new SemanticException("funkcija");
//            }
//        });
//
//        // napravi provjeru za sve djelokruge
//        Scope.globalScope.forEachChild(SemanticHelper::checkFunctionDeclarations);
        if (!definedFunctions.containsAll(declaredFunctions)) {
            throw new SemanticException("funkcija");
        }
    }

}
