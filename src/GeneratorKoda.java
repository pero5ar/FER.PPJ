import lab3.semantic.InputParser;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;
import lab3.models.Scope;
import lab3.models.SemanticNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class GeneratorKoda {

    public GeneratorKoda(SemanticNode treeRoot) {
        this.treeRoot = treeRoot;

        globalScope = new Scope();
    }

    /**
     * Program main entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) throws IOException {
        String lines;
        int i;
        InputParser inputParser = new InputParser();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((lines = reader.readLine()) != null) {
                inputParser.add(lines);
            }
        }

        SemanticNode generativeTree = inputParser.parseTree();

        SemantickiAnalizator semantic = new SemantickiAnalizator(generativeTree);
        semantic.analyze();
    }

    private SemanticNode treeRoot;

    private Scope globalScope;

    public void analyze(){
        try {
            treeRoot.check(globalScope);

            int i;
            // vidi: 4.4.7 (str. 72)
            SemanticHelper.postTreeWalk();
        } catch(SemanticException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println();
            System.out.println(e.output);
        }
    }

}
