package lab3.models;

import lab3.rules.Rule;
import lab3.rules.Rules;
import lab3.types.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * @author JJ
 */
public class SemanticNode {
    private List<SemanticNode> children = new ArrayList<>();

    private int depth;
    private String line;

    private String symbol;
    /**
     * Set to true if the symbol is terminal, false if symbol is nonterminal
     */
    private boolean terminalSymbol;
    private int lineNumber;
    private String value;
    private SemanticNode parent;
    private Type type;
    private Scope scope;
    private boolean isLValue = false;

    /**
     * Svojstvo ntip sluzi za prijenos jednog dijela informacije o tipu u sve deklaratore. Za varijable
     * brojevnog tipa ntip ce biti cijeli tip, za nizove ce biti tip elementa niza, a za funkcije
     * ce biti povratni tip.
     */
    private Type nType;

    public SemanticNode(int depth, String line) {
        this.depth = depth;
        this.line = line.trim();
    }

    /**
     * Constructor that determines depth from the number of spaces at the beginning of line.
     * @param line The input line, padded with spaces
     */
    public SemanticNode(String line) {
        this.line = line.trim();
        this.depth = line.indexOf(this.line);
    }

    public void addChild(SemanticNode node) {
        children.add(node);
    }

    public int getDepth() {
        return depth;
    }

    /**
     * Gets the view of the children list.
     * @return The view of children
     */
    public List<SemanticNode> getChildren() {
        return children;
    }

    public void forEachChild(Consumer<? super SemanticNode> consumer) {
        children.forEach(consumer);
    }

    public SemanticNode getChildAt(int i){
        return children.get(i);
    }

    /**
     * Gets the symbol of the child at i-th index.
     * @param i index of the child
     * @return Child's symbol
     */
    public String getChildSymbol(int i) {
        return getChildAt(i).symbol;
    }

    /**
     * Checks if the symbol of the child at i-th index is equal to given <code>compareTo</code>.
     * @param i index of the child
     * @param compareTo the symbol to compare the child's symbol to
     * @return true if the child's symbol is equal to given symbol, false otherwise
     */
    public boolean childSymbolEqual(int i, String compareTo) {
        return getChildSymbol(i).equals(compareTo);
    }

    public String fullTreeString() {
        StringBuilder sb = new StringBuilder();

        sb.append(toString());
        sb.append(System.lineSeparator());

        children.forEach(node -> sb.append(node.fullTreeString()));

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < depth; i++) sb.append(' ');
        sb.append(symbol);

        if (terminalSymbol) {
            sb.append(" ").append(lineNumber);
            sb.append(" ").append(value);
        }

        return sb.toString();
    }

    /**
     * Builds the tree, recursively filling all the non-settable fields with calculated values.
     */
    public void build() {
        String[] lineSplit = line.split(" ");

        if (children.isEmpty() /* ekvivalentno provjeri je li prvi znak '<' */) {
            setSymbol(lineSplit[0], true);
            lineNumber = Integer.parseInt(lineSplit[1]);
            value = lineSplit[2];
        } else {
            setSymbol(line, false);
            children.forEach(child -> {
                child.parent = this;
                child.build();
            });
        }
    }

    private void setSymbol(String symbol, boolean isTerminal) {
        this.symbol = symbol;
        this.terminalSymbol = isTerminal;
    }

    /**
     * Performs rule check on this node in the given scope.
     *
     * @param scope The scope in which the rule validation should be performed
     */
    public void check(Scope scope) {
        Rule rule = Rules.getRule(symbol);

        if (rule == null) {
            throw new NoSuchElementException("No rule for " + symbol);
        }
        rule.apply(scope, this);
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Type getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    private String getErrorLine() {
        return symbol + "(" + lineNumber + "," + value + ")";
    }

    public String errorOutput() {
        StringBuilder message = new StringBuilder();

        message.append(this.symbol);
        message.append(" ::= ");

        this.forEachChild(child -> {
            if (child.terminalSymbol) {
                message.append(child.getErrorLine());
            } else {
                message.append(child.symbol);
            }
            message.append(" ");
        });

        return message.toString().trim();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SemanticNode getParent() {
        return parent;
    }

    public boolean isLValue() {
        return isLValue;
    }

    public void setLValue(boolean LValue) {
        isLValue = LValue;
    }

    public void setNType(Type nType) {
        this.nType = nType;
    }
}