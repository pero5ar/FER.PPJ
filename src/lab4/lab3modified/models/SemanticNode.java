package lab4.lab3modified.models;

import lab4.lab3modified.rules.Rules;
import lab4.lab3modified.types.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author JJ
 */
public class SemanticNode {
    public final int depth;
    private String line;
    private String symbol;

    /**
     * Set to true if the symbol is terminal, false if symbol is nonterminal
     */
    private boolean terminalSymbol;
    private int lineNumber;
    private List<Type> types = null;
    private List<String> values = null;
    private String value;
    private Type type = null;
    private SemanticNode parent;
    private List<SemanticNode> children = new ArrayList<>();
    private boolean isLValue = false;

    /**
     * Svojstvo ntip sluzi za prijenos jednog dijela informacije o tipu u sve deklaratore. Za varijable
     * brojevnog tipa ntip ce biti cijeli tip, za nizove ce biti tip elementa niza, a za funkcije
     * ce biti povratni tip.
     */
    private Type nType;
    private int brElem = -1;

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
        // This may very well throw IndexOutOfBoundsException. If it does,
        // we should wrap it in SemanticException... To be seen.
        return children.get(i);
    }

    /**
     * Checks if the symbol of the child at i-th index is equal to given <code>compareTo</code>.
     * @param i index of the child
     * @param compareTo the symbol to compare the child's symbol to
     * @return true if the child's symbol is equal to given symbol, false otherwise
     */
    public boolean childSymbolEqual(int i, String compareTo) {
        return getChildAt(i).symbol.equals(compareTo);
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
        String[] lineSplit = line.split(" ", 3);

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

    /**
     * Performs rule check on this node in the given scope.
     *
     * @param scope The scope in which the rule validation should be performed
     */
    public void check(Scope scope) {
        Rules.getRule(symbol).check(scope, this);
    }

    private void setSymbol(String symbol, boolean isTerminal) {
        this.symbol = symbol;
        this.terminalSymbol = isTerminal;
    }

    public void setLValue(boolean LValue) {
        isLValue = LValue;
    }

    public void setBrElem(int brElem) {
        this.brElem = brElem;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setNType(Type nType) {
        this.nType = nType;
    }

    public Type getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    public SemanticNode getParent() {
        return parent;
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

    public boolean isLValue() {
        return isLValue;
    }

    public Type getNType() {
        return nType;
    }

    public List<Type> getTypes() {
        if (types == null) {
            return Collections.singletonList(type);
        }
        return types;
    }

    public List<String> getValues() {
        if (values == null) {
            return Collections.singletonList(value);
        }
        return values;
    }

    public int getBrElem() {
        return brElem;
    }
}