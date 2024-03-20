package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

public class Divide<T extends Number> extends AbstractBinaryOperation<T> {
    public Divide(TripleExpression<T> value1, TripleExpression<T> value2, TypeOperator<T> typeOperator) {
        super(value1, value2, typeOperator);
    }

    @Override
    protected T compute(T value1, T value2) {
        return typeOperator.divide(value1, value2);
    }

    @Override
    protected String getOperation() {
        return "/";
    }
}
