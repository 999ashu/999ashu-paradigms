package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

public class Negate<T extends Number> extends AbstractUnaryOperation<T> {
    public Negate(TripleExpression<T> value, TypeOperator<T> typeOperator) {
        super(value, typeOperator);
    }

    @Override
    protected T compute(T value) {
        return typeOperator.negate(value);
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
