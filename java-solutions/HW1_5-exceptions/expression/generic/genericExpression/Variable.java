package expression.generic.genericExpression;

import java.util.Objects;

public class Variable<T extends Number> implements TripleExpression<T> {
    private final String value;

    public Variable(String value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return switch (this.value) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new RuntimeException();
        };
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable<?> variable = (Variable<?>) o;
        return Objects.equals(value, variable.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
