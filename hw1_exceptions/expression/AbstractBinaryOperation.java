package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperation implements CustomExpression {
    private final CustomExpression value1;
    private final CustomExpression value2;

    public AbstractBinaryOperation(CustomExpression value1, CustomExpression value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public int evaluate(int x) {
        return compute(value1.evaluate(x), value2.evaluate(x));
    }

    protected abstract int compute(int value1, int value2);

    @Override
    public int evaluate(int x, int y, int z) {
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
        AbstractBinaryOperation that = (AbstractBinaryOperation) o;
        return Objects.equals(value1, that.value1) && Objects.equals(value2, that.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, getOperation());
    }
}