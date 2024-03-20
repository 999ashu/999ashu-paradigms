package expression.generic.typeOperators;

public interface TypeOperator<T extends Number> {
    T convert(int value);

    T parseNumber(String value);

    T add(T value1, T value2);

    T subtract(T value1, T value2);

    T multiply(T value1, T value2);

    T divide(T value1, T value2);

    T negate(T value);
}
