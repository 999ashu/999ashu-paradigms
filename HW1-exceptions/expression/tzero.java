package expression;

public class tzero extends AbstractUnaryOperation{
    public tzero(CustomExpression value) {
        super(value);
    }

    @Override
    protected int compute(int value) {
        return Integer.numberOfTrailingZeros(value);
    }

    @Override
    protected String getOperation() {
        return "t0";
    }
}
