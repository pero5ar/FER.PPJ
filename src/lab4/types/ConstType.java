package lab4.types;

public class ConstType extends PrimitiveType {
    public static final ConstType CONST_INT = new ConstType(IntType.INSTANCE);
    public static final ConstType CONST_CHAR = new ConstType(CharType.INSTANCE);

    private final NumberType wrappedType;

    private ConstType(NumberType type) {
        this.wrappedType = type;
    }

    public PrimitiveType getType() {
        return wrappedType;
    }

    @Override
    public boolean canImplicitCast(Type target) {
		// vidi 4.3.1 (str. 41)
        return this.equals(target) || wrappedType == target || wrappedType.canImplicitCast(target);
    }

    @Override
    public boolean canExplicitCast(Type target) {
        // vidi 4.3.1 (str. 41)
        return canImplicitCast(target) || wrappedType.canExplicitCast(target);
    }

    @Override
    public boolean equals(Type o) {
        if (!(o instanceof ConstType)) {
            return false;
        }

        ConstType otherConst = (ConstType) o;
        return this.wrappedType.equals(otherConst.wrappedType);
    }
}
