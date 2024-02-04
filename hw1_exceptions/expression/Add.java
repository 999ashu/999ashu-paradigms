package expression;

public class Add extends AbstractBinaryOperation {
    public Add(CustomExpression value1, CustomExpression value2) {
        super(value1, value2);
    }

    @Override
    protected int compute(int value1, int value2) {
        return value1 + value2;
    }

    @Override
    protected String getOperation() {
        return "+";
    }
}