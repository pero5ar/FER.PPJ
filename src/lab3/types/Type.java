package lab3.types;

/**
 * @author JJ
 */
public abstract class Type {
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public boolean canExplicitCast(Type target) {
        return canImplicitCast(target);
    }

    public abstract boolean canImplicitCast(Type target);

    public abstract boolean equals(Type o);

    public boolean equals(Object o) {
        // hack for list contains methods etc...
        if (o instanceof Type) {
            return equals((Type) o);
        }
        return super.equals(o);
    }
}
