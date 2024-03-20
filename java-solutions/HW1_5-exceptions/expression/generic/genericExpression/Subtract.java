package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

public class Subtract<T extends Number> extends AbstractBinaryOperation<T> {
    public Subtract(TripleExpression<T> value1, TripleExpression<T> value2, TypeOperator<T> typeOperator) {
        super(value1, value2, typeOperator);
    }

    @Override
    protected T compute(T value1, T value2) {
        return typeOperator.subtract(value1, value2);
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
