package expression.generic.typeOperators;

public class DoubleOperator implements TypeOperator<Double> {
    @Override
    public Double convert(int value) {
        return (double) value;
    }

    @Override
    public Double parseNumber(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public Double add(Double value1, Double value2) {
        return value1 + value2;
    }

    @Override
    public Double subtract(Double value1, Double value2) {
        return value1 - value2;
    }

    @Override
    public Double multiply(Double value1, Double value2) {
        return value1 * value2;
    }

    @Override
    public Double divide(Double value1, Double value2) {
        return value1 / value2;
    }

    @Override
    public Double negate(Double value) {
        return multiply(value, -1.0);
    }
}
