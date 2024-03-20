package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

public class Multiply<T extends Number> extends AbstractBinaryOperation<T> {
    public Multiply(TripleExpression<T> value1, TripleExpression<T> value2, TypeOperator<T> typeOperator) {
        super(value1, value2, typeOperator);
    }

    @Override
    protected T compute(T value1, T value2) {
        return typeOperator.multiply(value1, value2);
    }

    @Override
    protected String getOperation() {
        return "*";
    }
}
