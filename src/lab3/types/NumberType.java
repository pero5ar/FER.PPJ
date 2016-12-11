package lab3.types;

public abstract class NumberType extends PrimitiveType {
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
        if (target instanceof ConstType) {
            ConstType constType = (ConstType) target;
            return constType.getType() == this;
        }

        return super.canImplicitCast(target);
    }

    @Override
    public boolean canExplicitCast(Type target) {
        if (canImplicitCast(target)) {
            return true;
        }

        // vidi 4.3.1 (str. 41)
        if (target instanceof NumberType) {
            return true;
        }

        if (target instanceof ConstType) {
            ConstType constType = (ConstType) target;
            if (constType.getType() instanceof NumberType) {
                return true;
            }
        }

        return false;
    }
}
