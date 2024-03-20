package expression.generic.genericExpression;

import expression.generic.typeOperators.TypeOperator;

import java.util.Objects;

public abstract class AbstractUnaryOperation<T extends Number> implements TripleExpression<T> {
    protected final TypeOperator<T> typeOperator;
    private final TripleExpression<T> value;

    public AbstractUnaryOperation(TripleExpression<T> value, TypeOperator<T> typeOperator) {
        this.typeOperator = typeOperator;
        this.value = value;
    }

    protected abstract T compute(T value);

    @Override
    public T evaluate(T x, T y, T z) {
        return compute(value.evaluate(x, y, z));
    }

    protected abstract String getOperation();

    @Override
    public String toString() {
        return getOperation() + "(" + value.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUnaryOperation<?> that = (AbstractUnaryOperation<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
