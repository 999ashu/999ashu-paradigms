package expression;

import java.util.List;
import java.util.Objects;

public abstract class AbstractUnaryOperation implements CustomExpression {
    private final CustomExpression value;

    public AbstractUnaryOperation(CustomExpression value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return compute(value.evaluate(x));
    }

    protected abstract int compute(int value);

    @Override
    public int evaluate(int x, int y, int z) {
        return compute(value.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return compute(value.evaluate(variables));
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
        AbstractUnaryOperation that = (AbstractUnaryOperation) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getOperation());
    }
}