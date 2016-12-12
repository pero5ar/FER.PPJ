
import lab3.models.Scope;
import lab3.models.SemanticNode;
import lab3.semantic.InputParser;
import lab3.semantic.SemanticException;
import lab3.semantic.SemanticHelper;

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
            SemanticHelper.postTreeWalk();
        } catch(SemanticException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println();
            System.out.println(e.output);
        }
    }

}
