
import lab3.models.Scope;
import lab3.models.ScopeElement;
import lab3.models.SemanticNode;
import lab3.semantic.InputParser;
import lab3.semantic.SemanticException;
import lab3.types.FunctionType;
import lab3.types.IntType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JJ
 */
public class SemantickiAnalizator {

    /**
     * Program main entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) throws IOException {
        List<String> inputLines = new ArrayList<>();
        String lines;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while((lines = reader.readLine())!=null ) {
                if (lines.isEmpty()){
                    break;
                }
                inputLines.add(lines);
            }
        }

        InputParser inputParser = new InputParser(inputLines);
        SemanticNode generativeTree = inputParser.parseTree();

        SemantickiAnalizator semantic = new SemantickiAnalizator(generativeTree);
        semantic.analyze();
    }

    private SemanticNode treeRoot;

    private Scope globalScope;

    public SemantickiAnalizator(SemanticNode treeRoot) {
        this.treeRoot = treeRoot;

        globalScope = new Scope();
    }

    public void analyze(){
        try {
            treeRoot.check(globalScope);

            // vidi: 4.4.7 (str. 72)
            checkMain();
            checkFunctionDeclarations(globalScope);
        } catch(SemanticException e) {
            System.out.println(e.output);
            System.err.println(e.getMessage());
        }
    }

    private void checkMain() {
        ScopeElement main = globalScope.getElement("main");

        if (main == null) {
            throw new SemanticException("main", "Main must be declared.");
        }

        if (!main.defined) {
            throw new SemanticException("main", "Main must be defined.");
        }
        if (!(main.getType() instanceof FunctionType)) {
            throw new SemanticException("main", "Main must be a function.");
        }

        FunctionType mainType = (FunctionType) main.getType();
        if (!mainType.returnType.equals(IntType.INSTANCE)) {
            throw new SemanticException("main", "Main's return type must be int.");
        }
        if (!mainType.parameters.isEmpty()) {
            throw new SemanticException("main", "Main must accept no arguments (void).");
        }

        throw new SemanticException("main");
    }

    private void checkFunctionDeclarations(Scope scope) {
        scope.getElementTable().forEach((name, element) -> {
            if (!(element.getType() instanceof FunctionType)){
                // element nije funkcija; idi dalje
                return;
            }

            // globalna funkcija (deklaracija funkcije)
            ScopeElement globalFunction = globalScope.getElement(name);

            // globalna funkcija (odnosno deklaracija funkcije) mora postojati, /*biti definirana*/
            // i istog tipa kao i definicija funkcije
            if ( globalFunction == null /*|| !globalFunction.defined*/ || !element.getType().equals(globalFunction.getType()) ) {
                throw new SemanticException("funkcija", "Function declaration (" + name + ") error.");
            }
        });

        // napravi provjeru za sve djelokruge
        globalScope.forEachChild(this::checkFunctionDeclarations);
    }

}
