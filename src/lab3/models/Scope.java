package lab3.models;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represents a program scope.
 * @author JJ
 */
public class Scope {
    public static Scope globalScope;
    public final boolean isGlobal;
    private Set<Scope> children = new HashSet<>(); // TODO maybe linked set? is ordering important?
    private Scope parent;

    /**
     * tablica znakova
     */
    private Map<String, ScopeElement> elementTable = new HashMap<>();

    public Scope(Scope parent) {
        if (parent == null) {
            isGlobal = true;
            return;
        }

        this.parent = parent;
        parent.addChild(this);
        isGlobal = false;
    }

    /**
     * global scope
     */
    public Scope() {
        if (globalScope != null) {
            throw new IllegalStateException("Global state already set.");
        }

        isGlobal = true;
        globalScope = this;
    }

    private void addChild(Scope child) {
        Objects.requireNonNull(child);

        children.add(child);
    }

    /**
     * Checks whether the element with given name is declared in this scope.
     * @param name Element name
     * @return true if the element is declared, false otherwise
     */
    public boolean isDeclared(String name) {
        return isDeclared(name, false);
    }

    /**
     * Checks whether the element with given name is declared in this or, optionally any of the parent scopes.
     * @param name Element name
     * @param global If true, will check also all parents for declaration
     * @return true if the element is declared, false otherwise
     */
    public boolean isDeclared(String name, boolean global) {
        return (elementTable.containsKey(name) ||
                (
                        global &&
                        parent != null && parent.isDeclared(name, true)
                )
        );
    }

    /**
     * Gets a declared element from the scope, and optionally from any
     * of the parent scopes.
     * @param name Element name
     * @param global If true, will look for the element in all the parent scopes
     * @return The declared element with given name. Null if element is not declared.
     */
    public ScopeElement getElement(String name, boolean global) {
        ScopeElement element = elementTable.get(name);
        if (element != null) {
            return element;
        }

        if (!global || parent == null) {
            return null;
        }

        return parent.getElement(name, true);
    }

    public void addElement(String name, ScopeElement element) {
        elementTable.put(name, element);
    }

    /**
     * Checks whether an element is both declared <b>and defined</b>.
     * @param name Element name
     * @param global If true, will look for definition in all the parent scopes.
     * @return true if the element is defined, false otherwise
     */
    public boolean isDefined(String name, boolean global) {
        ScopeElement element = getElement(name, global);
        return element != null && element.defined;
    }

    public Map<String, ScopeElement> getElementTable() {
        return elementTable;
    }

    public void forEachChild(Consumer<? super Scope> consumer) {
        children.forEach(consumer);
    }
}
