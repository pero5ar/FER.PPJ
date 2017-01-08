import lab4.lab3modified.semantic.InputParser;
import lab4.lab3modified.semantic.SemanticException;
import lab4.lab3modified.semantic.SemanticHelper;
import lab4.lab3modified.models.Scope;
import lab4.lab3modified.models.SemanticNode;
import lab4.frisc.CodeGenerator;

import java.io.*;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class GeneratorKoda {

    private static final String OUT_PATH = "a.frisc";

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
        InputParser inputParser = new InputParser();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((lines = reader.readLine()) != null) {
                inputParser.add(lines);
            }
        }

        SemanticNode generativeTree = inputParser.parseTree();

        GeneratorKoda semantic = new GeneratorKoda(generativeTree);
        semantic.analyze();

        print();
    }

    private static void print() {
        try {
            File file = new File(OUT_PATH);
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            CodeGenerator.getInstance().generateCode().forEach(line -> writer.println(line));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SemanticNode treeRoot;

    private Scope globalScope;

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
