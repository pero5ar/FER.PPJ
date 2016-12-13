package lab3.types;

import java.util.Iterator;
import java.util.List;

public class FunctionType extends Type {
    public final Type returnType;

    public final List<Type> parameters;

    public FunctionType(Type returnType, List<Type> parameters) {
        this.returnType = returnType;

        if (parameters != null && parameters.isEmpty()) {
            parameters = null;
        }
        this.parameters = parameters;
    }

    @Override
    public boolean canImplicitCast(Type target) {
        if (!(target instanceof FunctionType)) {
            return false;
        }

        FunctionType function = (FunctionType) target;

        Iterator<Type> functionIter = function.parameters.iterator();
        for(Type param : parameters) {
            if (!functionIter.hasNext()) {
                throw new IllegalStateException("Non matching count");
            }
            if (!param.canImplicitCast(functionIter.next())) {
                return false;
            }
        }
        return returnType.canImplicitCast(function.returnType);
    }

    @Override
    public boolean equals(Type t) {
        if (this == t) return true;
        if (!(t instanceof FunctionType)) return false;

        FunctionType that = (FunctionType) t;

        return (returnType != null ? returnType.equals(that.returnType) : that.returnType == null)
                && (parameters != null ? parameters.equals(that.parameters) : that.parameters == null);
    }
}
