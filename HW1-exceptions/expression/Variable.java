package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements CustomExpression {
    private final String value;
    private int index;

    public Variable(String value) {
        this.value = value;
    }

    public Variable(int value) {
        this.index = value;
        this.value = "$" + value;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (this.value) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> index;
        };
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(index);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return index == variable.index && Objects.equals(value, variable.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, index);
    }
}
