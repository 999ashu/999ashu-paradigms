package expression;

public class Negate extends AbstractUnaryOperation {
    public Negate(CustomExpression value) {
        super(value);
    }

    @Override
    protected int compute(int value) {
        return -value;
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}