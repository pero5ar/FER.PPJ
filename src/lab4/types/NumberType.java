package lab4.types;

public abstract class NumberType extends PrimitiveType {
    private static final long serialVersionUID = 5446320898597838475L;

    /**
     * Inclusive min value
     */
    public final long MIN_VALUE;

    /**
     * Inclusive max value
     */
    public final long MAX_VALUE;

    protected NumberType(long minValue, long maxValue) {
        MIN_VALUE = minValue;
        MAX_VALUE = maxValue;
    }

    public boolean validRange(String value) {
        try {
            Long longValue = Long.decode(value);

            return longValue >= MIN_VALUE && longValue <= MAX_VALUE;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public boolean canImplicitCast(Type target) {
        // vidi 4.3.1 (str. 41)
        if (!(target instanceof ConstType)) {
            return this.equals(target);
        }

        return this == ((ConstType)target).getType();
    }

    @Override
    public boolean canExplicitCast(Type target) {
        // vidi 4.3.1 (str. 41)

        if (target instanceof NumberType || canImplicitCast(target)) {
            return true;
        } else if (!(target instanceof ConstType)) {
            return false;
        }

        ConstType constType = (ConstType) target;
        return (constType.getType() instanceof NumberType);
    }
}
