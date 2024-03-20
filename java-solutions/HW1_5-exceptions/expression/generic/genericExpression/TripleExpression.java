package expression.generic.genericExpression;

public interface TripleExpression<T extends Number> {
    T evaluate(T x, T y, T z);
}
