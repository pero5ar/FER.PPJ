package lab3.types;

/**
 * @author JJ
 */
public abstract class Type {
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public boolean canImplicitCast(Type target) {
        return this == target;
    }

    public boolean canExplicitCast(Type target) {
        return canImplicitCast(target);
    }

    public abstract boolean equals(Type o);
}
