package lab3.semantic;

import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.types.FunctionType;
import lab3.types.IntType;
import lab3.types.Type;

import java.util.ArrayList;
import java.util.List;

public class SemanticHelper {
    /**
     * Throws exception if the expression is false
     */
    public static void assertTrue(boolean expression, RuntimeException exception) {
        if (exception == null) {
            throw new IllegalArgumentException("Exception mustn't be null.");
        }

        if (!expression) {
            throw exception;
        }
    }

    private static List<DeclaredFunction> declaredFunctions = new ArrayList<>();
    private static List<DeclaredFunction> definedFunctions = new ArrayList<>();

    /**
     * Vidi: 4.4.7 (str. 72)
     */
    public static void postTreeWalk() {
        if (!validateMain()) {
            throw new SemanticException("main");
        }

        checkFunctionDeclarations(Scope.globalScope);
    }

    public static void addDeclaredFunction(String name, Type type) {
        declaredFunctions.add(new DeclaredFunction(name, type));
    }

    public static void addDefinedFunction(String name, Type type) {
        definedFunctions.add(new DeclaredFunction(name, type));
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
        return mainType.returnType.equals(IntType.INSTANCE) &&
                (mainType.parameters == null || mainType.parameters.isEmpty());
    }

    private static void checkFunctionDeclarations(Scope scope) {
        assertTrue(
                definedFunctions.containsAll(declaredFunctions),
                new SemanticException("funkcija")
        );
    }

    private static class DeclaredFunction{
        private String name;
        private Type type;

        private DeclaredFunction(String name, Type type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DeclaredFunction)) return false;

            DeclaredFunction that = (DeclaredFunction) o;

            return (name != null ? name.equals(that.name) : that.name == null) && (type != null ? type.equals(that.type) : that.type == null);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }

}
