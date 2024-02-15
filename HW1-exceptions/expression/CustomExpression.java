package expression;

public interface CustomExpression extends Expression, TripleExpression, ListExpression {
    void toStringBuilder(StringBuilder sb);
}
