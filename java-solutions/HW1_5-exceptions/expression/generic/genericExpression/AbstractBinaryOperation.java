package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

import java.util.Objects;

public abstract class AbstractBinaryOperation<T extends Number> implements TripleExpression<T> {
    protected final TypeOperator<T> typeOperator;
    private final TripleExpression<T> value1;
    private final TripleExpression<T> value2;

    public AbstractBinaryOperation(
            TripleExpression<T> value1, TripleExpression<T> value2, TypeOperator<T> typeOperator
    ) {
        this.typeOperator = typeOperator;
        this.value1 = value1;
        this.value2 = value2;
    }

    protected abstract T compute(T value1, T value2);

    @Override
    public T evaluate(T x, T y, T z) {
        return compute(value1.evaluate(x, y, z), value2.evaluate(x, y, z));
    }

    protected abstract String getOperation();

    @Override
    public String toString() {
        return "(" + value1.toString() + " " + getOperation() + " " + value2.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBinaryOperation<?> that = (AbstractBinaryOperation<?>) o;
        return Objects.equals(value1, that.value1) && Objects.equals(value2, that.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }
}
