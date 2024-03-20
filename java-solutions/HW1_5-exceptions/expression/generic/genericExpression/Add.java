package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

public class Add<T extends Number> extends AbstractBinaryOperation<T> {
    public Add(TripleExpression<T> value1, TripleExpression<T> value2, TypeOperator<T> typeOperator) {
        super(value1, value2, typeOperator);
    }

    @Override
    protected T compute(T value1, T value2) {
        return typeOperator.add(value1, value2);
    }

    @Override
    protected String getOperation() {
        return "+";
    }
}
