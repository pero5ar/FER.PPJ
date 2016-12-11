package lab3.models;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represents a program scope.
 * @author JJ
 */
public class Scope {
    private Scope parent;
    private Set<Scope> children = new HashSet<>(); // TODO maybe linked set? is ordering important?

    /**
     * tablica znakova
     */
    private Map<String, ScopeElement> elementTable = new HashMap<>();

    public Scope() {
    }

    public Scope(Scope parent) {
        Objects.requireNonNull(parent);

        this.parent = parent;
        parent.addChild(this);
    }

    private void addChild(Scope child) {
        Objects.requireNonNull(child);

        children.add(child);
    }

    /**
     * Checks whether the element with given name is declared in this or any of the parent scopes.
     * @param name Element name
     * @return true if the element is declared, false otherwise
     */
    public boolean isDeclared(String name) {
        return (elementTable.containsKey(name) ||
                (
                    parent != null && parent.isDeclared(name)
                )
        );
    }

    /**
     * Gets a declared element from the scope, or any of the parent scopes.
     * @param name Element name
     * @return The declared element with given name. Null if element is not declared.
     */
    public ScopeElement getElement(String name) {
        ScopeElement element = elementTable.get(name);
        if (element != null) {
            return element;
        }

        if (parent == null) {
            return null;
        }

        return parent.getElement(name);
    }

    public void addElement(String name, ScopeElement element) {
        elementTable.put(name, element);
    }

    /**
     * Checks whether an element is both declared <b>and defined</b>.
     * @param name Element name
     * @return true if the element is defined, false otherwise
     */
    public boolean isDefined(String name) {
        ScopeElement element = getElement(name);
        return element != null && element.defined;
    }

    public Map<String, ScopeElement> getElementTable() {
        return elementTable;
    }

    public void forEachChild(Consumer<? super Scope> consumer) {
        children.forEach(consumer);
    }
}
