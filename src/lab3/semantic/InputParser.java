package lab3.semantic;

import lab3.models.SemanticNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author JJ
 */
public class InputParser {
    private final List<String> inputLines;

    private final Stack<SemanticNode> stack;

    private SemanticNode root;

    public InputParser() {
        this.stack = new Stack<>();
        this.inputLines = new ArrayList<>();
    }

    public void add(String line) {
        inputLines.add(line);
    }

    /**
     * Parses the input lines representing generative tree.
     * @return The root node of parsed tree
     */
    public SemanticNode parseTree() {
        if (root == null) {
            root = buildTree();
        }

        return root;
    }

    /**
     * Method that actually performs the tree building. Returns the root node of the parsed tree.
     * @return The root node of parsed tree
     */
    private SemanticNode buildTree() {
        for(String line : inputLines) {
            SemanticNode node = new SemanticNode(line);

            if (!stack.empty() && node.depth <= stack.peek().depth) {
                collapseStackTop(node.depth);
            }
            stack.push(node);
        }

        collapseStackTop(1);
        SemanticNode root = stack.pop();

        root.build();

        return root;
    }

    /**
     * Pops the top node from the stack and adds it as a child to the node beneath,
     * as long as the depth of top node is greater than or equal to minDepth.
     * @param minDepth If the top of stack's depth is less than minDepth, method quits.
     */
    private void collapseStackTop(int minDepth) {
        if (stack.size() <= 1) {
            return;
        }
        if (stack.peek().depth < minDepth) {
            return;
        }

        SemanticNode child = stack.pop();
        stack.peek().addChild(child);
        collapseStackTop(minDepth);
    }

}
