package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements CustomExpression {
    private String stringValue;
    private int intValue;

    public Variable(String value) {
        this.stringValue = value;
    }

    public Variable(int value) {
        this.intValue = value;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (stringValue) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public int evaluate(List<Integer> variables) {
        if (intValue > 0) {
            return variables.get(intValue);
        } else {
            return variables.get(Integer.parseInt(stringValue.substring(1)));
        }
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(stringValue, variable.stringValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringValue);
    }
}
